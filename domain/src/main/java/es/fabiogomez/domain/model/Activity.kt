package es.fabiogomez.domain.model

import java.io.Serializable
import java.util.*

data class Activity(
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
        Activities(ArrayList<Activity>())
    }
}

class Activities(val activities: MutableList<Activity>) : Aggregate<Activity> {

    override fun count(): Int {
        return activities.size
    }

    override fun All(): List<Activity> {
        return activities
    }

    override operator fun get(position: Int): Activity {
        return activities.get(position)
    }

    override fun add(element: Activity) {
        activities.add(element)
    }

    override fun delete(position: Int) {
        activities.removeAt(position)
    }

    override fun delete(element: Activity) {
        activities.remove(element)
    }

}