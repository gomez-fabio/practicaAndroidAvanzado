package es.fabiogomez.repository

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import es.fabiogomez.repository.cache.Cache
import es.fabiogomez.repository.cache.CacheImpl
import es.fabiogomez.repository.model.ActivitiesResponseEntity
import es.fabiogomez.repository.model.ActivityEntity
import es.fabiogomez.repository.model.ShopEntity
import es.fabiogomez.repository.model.ShopsResponseEntity
import es.fabiogomez.repository.network.GetJsonManager
import es.fabiogomez.repository.network.GetJsonManagerVolleyImpl
import es.fabiogomez.repository.network.json.JsonEntitiesParser
import java.lang.ref.WeakReference


class RepositoryImpl(context: Context): Repository {

    private val weakContext = WeakReference<Context>(context)
    private val cache : Cache = CacheImpl(weakContext.get()!!)



    // GETS

    override fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // Read all Shops from cache
        cache.getAllShops(
            success = {
                // if there's shops in cache then return them
                Log.d("Repository","RepositoryImpl.getAllShops success")
                success(it)
            },
            error= {
                // if no shops in cache the go to network, perform request and store results in cache
                Log.d("Repository","💩 RepositoryImpl.getAllShops error")
                populateShopsCache(success, error)
            })
    }

    override fun getAllActivities(success: (activities: List<ActivityEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // Read all Activities from cache
        cache.getAllActivities(
                success = {
                    // if there's activities in cache then return them
                    Log.d("Repository","RepositoryImpl.getAllActivities success")
                    success(it)
                },
                error= {
                    // if no activities in cache the go to network, perform request and store results in cache
                    Log.d("Repository","💩 RepositoryImpl.getAllActivities error")
                    populateActivitiesCache(success, error)
                })
    }


    // DELETES

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        cache.deleteAllShops(success, error)
    }

    override fun deleteAllActivities(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        cache.deleteAllActivities(success, error)
    }



    // CACHING

    private fun populateShopsCache(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // perform network request

        val jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get() !!)
        jsonManager.execute(BuildConfig.MADRID_SHOPS_GETSHOPS_SERVER_URL, success =  object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                val parser = JsonEntitiesParser()
                var responseEntity: ShopsResponseEntity

                try {
                    responseEntity = parser.parse<ShopsResponseEntity>(e)
                } catch (e: InvalidFormatException) {
                    error("💩 RepositoryImpl.populateShopsCache parsing error")
                    return
                }

                // store result in cache
                cache.saveAllShops(responseEntity.result, success = {
                    success(responseEntity.result)
                }, error = {
                    error("💩 RepositoryImpl.populateShopsCache cache populating error")
                })
            }
        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
            }
        })
    }

    private fun populateActivitiesCache(success: (activities: List<ActivityEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // perform network request

        val jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get() !!)
        jsonManager.execute(BuildConfig.MADRID_SHOPS_GETACTIVITIES_SERVER_URL, success =  object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                val parser = JsonEntitiesParser()
                var responseEntity: ActivitiesResponseEntity

                try {
                    responseEntity = parser.parse<ActivitiesResponseEntity>(e)
                } catch (e: InvalidFormatException) {
                    error("💩 RepositoryImpl.populateActivitiesCache parsing error")
                    return
                }

                // store result in cache
                cache.saveAllActivities(responseEntity.result, success = {
                    success(responseEntity.result)
                }, error = {
                    error("💩 RepositoryImpl.populateActivitiesCache cache populating error")
                })
            }
        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
            }
        })
    }
}