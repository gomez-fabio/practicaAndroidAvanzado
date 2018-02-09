package es.fabiogomez.repository

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import es.fabiogomez.repository.cache.Cache
import es.fabiogomez.repository.cache.CacheImpl
import es.fabiogomez.repository.model.ShopEntity
import es.fabiogomez.repository.model.ShopsResponseEntity
import es.fabiogomez.repository.network.GetJsonManager
import es.fabiogomez.repository.network.GetJsonManagerVolleyImpl
import es.fabiogomez.repository.network.json.JsonEntitiesParser
import java.lang.ref.WeakReference


class RepositoryImpl(context: Context): Repository {

    private val weakContext = WeakReference<Context>(context)
    private val cache : Cache = CacheImpl(weakContext.get()!!)

    override fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // Read all Shops from cache
        cache.getAllShops(
            success = {
                // if there's shops in cache then return them
                Log.d("JURJUR","getAllShops success")
                success(it)
            },
            error= {
                // if no shops in cache the go to network, perform request and store results in cache
                Log.d("JURJUR","getAllShops error, populating")
                populateCache(success, error)
            })
    }

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        cache.deleteAllShops(success, error)
    }

    private fun populateCache(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // perform network request

        val jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get() !!)
        jsonManager.execute(BuildConfig.MADRID_SHOPS_SERVER_URL, success =  object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                val parser = JsonEntitiesParser()
                var responseEntity: ShopsResponseEntity

                try {
                    responseEntity = parser.parse<ShopsResponseEntity>(e)
                } catch (e: InvalidFormatException) {
                    error("Error parsing")
                    return
                }

                // store result in cache
                cache.saveAllShops(responseEntity.result, success = {
                    success(responseEntity.result)
                }, error = {
                    error("JARLL error populating cache")
                })
            }
        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
            }
        })
    }
}