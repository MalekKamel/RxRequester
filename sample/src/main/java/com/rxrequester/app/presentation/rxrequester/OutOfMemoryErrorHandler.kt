package com.rxrequester.app.presentation.rxrequester

import com.rxrequester.app.R
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.exception.handler.throwable.ThrowableHandler

class OutOfMemoryErrorHandler : ThrowableHandler<OutOfMemoryError>() {

    override fun supportedErrors(): List<Class<OutOfMemoryError>> {
        return listOf(OutOfMemoryError::class.java)
    }

    override fun handle(throwable: Throwable, presentable: Presentable) {
        presentable.showError(R.string.no_memory_free_up_space)
    }
}
