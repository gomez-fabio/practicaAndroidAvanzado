package es.fabiogomez.repository.network

import es.fabiogomez.repository.ErrorCompletion
import es.fabiogomez.repository.SuccessCompletion

internal interface GetJsonManager {
    fun execute(url: String, success: SuccessCompletion<String>, error:ErrorCompletion)
}