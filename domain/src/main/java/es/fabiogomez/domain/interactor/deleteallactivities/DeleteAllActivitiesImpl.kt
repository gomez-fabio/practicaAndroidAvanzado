package es.fabiogomez.domain.interactor.deleteallactivities

import android.content.Context
import es.fabiogomez.domain.interactor.CodeClosure
import es.fabiogomez.domain.interactor.ErrorClosure
import es.fabiogomez.repository.RepositoryImpl
import java.lang.ref.WeakReference

class DeleteAllActivitiesImpl(context: Context): DeleteAllActivities {

    val weakContext = WeakReference<Context>(context)

    override fun execute(success: CodeClosure, error: ErrorClosure) {
        val repository = RepositoryImpl(weakContext.get()!!)

        repository.deleteAllShops(success, error)
    }
}