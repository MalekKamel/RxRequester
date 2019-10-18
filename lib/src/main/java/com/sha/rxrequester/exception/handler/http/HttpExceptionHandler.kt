package com.sha.rxrequester.exception.handler.http

/**
 * Created by Sha on 10/9/17.
 */

abstract class HttpExceptionHandler {

    protected abstract fun supportedErrors(): List<Int>

    fun canHandle(code: Int): Boolean {
        return supportedErrors().any { item -> code == item }
    }

    abstract fun handle(info: HttpExceptionInfo)

}
