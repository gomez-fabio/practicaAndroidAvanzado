package es.fabiogomez.repository

import es.fabiogomez.repository.model.ShopEntity

interface Repository {
    fun deleteAllShops(success: () -> Unit, error: (errorMessage:String)-> Unit)

    fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage:String)-> Unit)
}
