package es.fabiogomez.repository.network

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import es.fabiogomez.repository.ErrorCompletion
import es.fabiogomez.repository.SuccessCompletion
import java.lang.ref.WeakReference

internal class GetJsonManagerVolleyImpl (context: Context): GetJsonManager {

     var weakContext: WeakReference<Context> = WeakReference(context)     // Referencia débil para evitar leaks de memoria por el ciclo siguiente, dada una actividad que inicia la descarga se produce el siguente grafo
     var requestQueue: RequestQueue? = null                               // Activity (strong)--> Interactor (strong)--> Repository (strong)--> Volley (weak)~~> Activity (el padre)

     override fun execute(url: String, success: SuccessCompletion<String>, error: ErrorCompletion) {

         // create request ( success, failure)
        var request = StringRequest(url,
            Response.Listener {
                Log.d("JSON", it)                           // Bloque de success
                success.successCompletion(it)
            }, Response.ErrorListener {
                error.errorCompletion(it.localizedMessage)      // Bloque de error
            })

         // add request to queue
         requestQueue().add(request)

     }

    // get request queue
    fun requestQueue(): RequestQueue {
        if (requestQueue == null ) {
            // Me creo la cola pasándole como contexto el contexto que me pasan
            requestQueue = Volley.newRequestQueue(weakContext.get())
        }

        return requestQueue!!
    }
 }