package es.fabiogomez.madridshops

import android.support.multidex.MultiDexApplication
import android.util.Log
import es.fabiogomez.domain.interactor.ErrorCompletion
import es.fabiogomez.domain.interactor.SuccessCompletion
import es.fabiogomez.domain.interactor.getallshops.GetAllShopsInteractorImpl
import es.fabiogomez.domain.model.Shops

// esta clase es el equivalente al appdelegate de ios
class MadridShopsApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // init code application wide, base de datos, inyeccion de dependencias, etc...

        val allShopsInteractor = GetAllShopsInteractorImpl(this)

        allShopsInteractor.execute(
        success = object : SuccessCompletion<Shops>{
            override fun successCompletion(shops: Shops) {
                Log.d("Shops", "Count:" + shops.count())

                shops.shops.forEach { Log.d("Shop", it.name) }
            }
        },
        error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Log.d("Shops", errorMessage)
            }
        })

//        DeleteAllShopsImpl(this).execute(success = {
//            Log.d("JARLL", "success!")
//        }, error = {
//            Log.d("JARLL", "error deleting!")
//        })
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

}