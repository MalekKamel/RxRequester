package com.sha.rxrequester.exception.handler.nonhttp

/**
 * Created by Sha on 10/9/17.
 */

abstract class NonHttpExceptionHandler<T: Throwable> {

    protected abstract fun supportedThrowables(): List<Class<out T>>

    fun canHandle(throwable: Throwable): Boolean {
        return supportedThrowables().any { t -> t.isAssignableFrom(throwable.javaClass) }
    }
    
    abstract fun handle(info: NonHttpExceptionInfo)


}
