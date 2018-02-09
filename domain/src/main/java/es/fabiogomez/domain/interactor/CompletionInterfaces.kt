package es.fabiogomez.domain.interactor

interface SuccessCompletion <T> {
    fun successCompletion(e: T)
}

interface ErrorCompletion {
    fun errorCompletion(errorMessage: String)
}