package es.fabiogomez.domain.interactor.getallactivities

import android.content.Context
import es.fabiogomez.domain.interactor.ErrorCompletion
import es.fabiogomez.domain.interactor.SuccessCompletion
import es.fabiogomez.domain.model.Activities
import es.fabiogomez.domain.model.Activity
import es.fabiogomez.repository.Repository
import es.fabiogomez.repository.RepositoryImpl
import es.fabiogomez.repository.db.stringToFloat
import es.fabiogomez.repository.model.ActivityEntity
import java.lang.ref.WeakReference

class GetAllActivitiesInteractorImpl(context: Context) : GetAllActivitiesInteractor {
    private val weakContext = WeakReference<Context>(context)
    private val repository : Repository = RepositoryImpl(weakContext.get()!!)

    override fun execute(success: SuccessCompletion<Activities>, error: ErrorCompletion) {
        repository.getAllActivities(
                success = {
                    val activities: Activities = entityMapper(it)
                    success.successCompletion(activities)
                }, error = {
                   error(it)
                })
            }

    private fun entityMapper(list: List<ActivityEntity>): Activities {
        val tempList = ArrayList<Activity>()
        list.forEach {
            val activity = Activity(
                        it.id.toInt(),
                        it.name,
                        it.address,
                        it.description,
                        stringToFloat(it.latitude),
                        stringToFloat(it.longitude),
                        it.img,
                        it.logo,
                        it.openingHours)

            tempList.add(activity)
        }

        val activities = Activities(tempList)
        return activities


    }
}


