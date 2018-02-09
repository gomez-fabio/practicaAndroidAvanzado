package es.fabiogomez.domain.interactor.internetstatus

import es.fabiogomez.domain.interactor.CodeClosure
import es.fabiogomez.domain.interactor.ErrorClosure


interface InternetStatusInteractor {
    fun execute(success: CodeClosure, error: ErrorClosure)
}