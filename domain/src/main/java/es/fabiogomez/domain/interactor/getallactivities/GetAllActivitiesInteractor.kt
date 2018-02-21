package es.fabiogomez.domain.interactor.getallactivities

import es.fabiogomez.domain.interactor.ErrorCompletion
import es.fabiogomez.domain.interactor.SuccessCompletion
import es.fabiogomez.domain.model.Activities
import es.fabiogomez.domain.model.Shops

interface GetAllActivitiesInteractor {
    fun execute(success: SuccessCompletion<Activities>, error: ErrorCompletion)
}