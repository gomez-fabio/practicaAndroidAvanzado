package es.fabiogomez.domain.model

import java.io.Serializable
import java.util.*

data class Shop(
                val id: Int,
                val name: String,
                val address: String,
                val description_en: String,
                val latitude: Float?,
                val longitude: Float?,
                val img: String,
                val logo: String,
                val openingHours_en: String): Serializable {
    init {
        Shops(ArrayList<Shop>())
    }
}

class Shops(val shops: MutableList<Shop>) : Aggregate<Shop> {

    override fun count(): Int {
        return shops.size
    }

    override fun All(): List<Shop> {
        return shops
    }

    override fun get(position: Int): Shop {
        return shops.get(position)
    }

    override fun add(element: Shop) {
        shops.add(element)
    }

    override fun delete(position: Int) {
        shops.removeAt(position)
    }

    override fun delete(element: Shop) {
        shops.remove(element)
    }

}