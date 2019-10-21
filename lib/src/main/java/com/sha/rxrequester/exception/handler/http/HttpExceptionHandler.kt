package com.sha.rxrequester.exception.handler.http

/**
 * Created by Sha on 10/9/17.
 */

abstract class HttpExceptionHandler {

    /**
     * errors that this handler can handle
     */
    protected abstract fun supportedErrors(): List<Int>

    /**
     * maps the error with supported errors
     * @return true if it's supported
     */
    fun canHandle(code: Int): Boolean {
        return supportedErrors().any { item -> code == item }
    }

    /**
     * handle the error
     */
    abstract fun handle(info: HttpExceptionInfo)

}
