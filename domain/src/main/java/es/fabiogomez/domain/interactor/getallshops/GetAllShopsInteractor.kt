package es.fabiogomez.domain.interactor.getallshops

import es.fabiogomez.domain.interactor.ErrorCompletion
import es.fabiogomez.domain.interactor.SuccessCompletion
import es.fabiogomez.domain.model.Shops

interface GetAllShopsInteractor {
    fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion)
}