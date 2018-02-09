package es.fabiogomez.domain.interactor.internetstatus

import es.fabiogomez.domain.interactor.CodeClosure
import es.fabiogomez.domain.interactor.ErrorClosure

class InternetStatusInteractorImpl : InternetStatusInteractor {
    override fun execute(success: CodeClosure, error: ErrorClosure) {
        success()
    }
}