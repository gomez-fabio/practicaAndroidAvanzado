package es.fabiogomez.repository.cache

import es.fabiogomez.repository.model.ActivityEntity
import es.fabiogomez.repository.model.ShopEntity


internal interface Cache {
    fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage:String)-> Unit)
    fun saveAllShops(shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage:String)-> Unit)

    fun deleteAllActivities(success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun getAllActivities(success: (activities: List<ActivityEntity>) -> Unit, error: (errorMessage:String)-> Unit)
    fun saveAllActivities(activities: List<ActivityEntity>, success: () -> Unit, error: (errorMessage:String)-> Unit)

}