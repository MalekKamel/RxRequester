package com.sha.rxrequester.exception.handler.throwable

/**
 * Created by Sha on 10/9/17.
 */

abstract class ThrowableHandler<T: Throwable> {

    /**
     * errors that this handler can handle
     */
    protected abstract fun supportedErrors(): List<Class<out T>>

    /**
     * maps the error with supported errors
     * @return true if it's supported
     */
    fun canHandle(throwable: Throwable): Boolean {
        return supportedErrors().any { t -> t.isAssignableFrom(throwable.javaClass) }
    }

    /**
     * handle the error
     */
    abstract fun handle(info: ThrowableInfo)


}
