package com.sha.rxrequester.handler

import com.sha.rxrequester.Presentable
import com.sha.rxrequester.exception.handler.throwable.ThrowableHandler

class OutOfMemoryErrorHandler : ThrowableHandler<OutOfMemoryError>() {

    override fun supportedErrors(): List<Class<OutOfMemoryError>> {
        return listOf(OutOfMemoryError::class.java)
    }

    override fun handle(throwable: Throwable, presentable: Presentable){
        presentable.showError("OutOfMemoryError")
    }
}
