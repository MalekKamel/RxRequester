package com.sha.rxrequester.exception.handler.throwable

/**
 * Created by Sha on 10/9/17.
 */

abstract class ThrowableHandler<T: Throwable> {

    protected abstract fun supportedErrors(): List<Class<out T>>

    fun canHandle(throwable: Throwable): Boolean {
        return supportedErrors().any { t -> t.isAssignableFrom(throwable.javaClass) }
    }
    
    abstract fun handle(info: ThrowableInfo)


}
