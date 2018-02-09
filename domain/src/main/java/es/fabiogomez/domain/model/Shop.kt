package es.fabiogomez.domain.model

import java.util.*

data class Shop(val id: Int, val name: String, val address: String) {
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