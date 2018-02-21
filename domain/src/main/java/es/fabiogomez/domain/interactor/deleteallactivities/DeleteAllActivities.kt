package es.fabiogomez.domain.interactor.deleteallactivities

import es.fabiogomez.domain.interactor.CodeClosure
import es.fabiogomez.domain.interactor.ErrorClosure

interface DeleteAllActivities {
    fun execute(success: CodeClosure, error: ErrorClosure)
}