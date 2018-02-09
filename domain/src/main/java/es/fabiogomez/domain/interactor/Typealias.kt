package es.fabiogomez.domain.interactor

import es.fabiogomez.domain.model.Shops


typealias SuccessClosure = (shops: Shops) -> Unit
typealias ErrorClosure = (msg: String) -> Unit
typealias CodeClosure = () -> Unit