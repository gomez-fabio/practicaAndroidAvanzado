package es.fabiogomez.domain.interactor.deleteallshops

import es.fabiogomez.domain.interactor.CodeClosure
import es.fabiogomez.domain.interactor.ErrorClosure

interface DeleteAllShops {
    fun execute(success: CodeClosure, error: ErrorClosure)
}