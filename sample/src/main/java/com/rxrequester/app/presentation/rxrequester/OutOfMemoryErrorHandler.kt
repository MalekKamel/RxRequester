package com.rxrequester.app.presentation.rxrequester

import com.rxrequester.app.R
import com.sha.rxrequester.exception.handler.throwable.ThrowableHandler
import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo

class OutOfMemoryErrorHandler : ThrowableHandler<OutOfMemoryError>() {

    override fun supportedErrors(): List<Class<OutOfMemoryError>> {
        return listOf(OutOfMemoryError::class.java)
    }

    override fun handle(info: ThrowableInfo) {
        info.presentable.showError(R.string.no_memory_free_up_space)
    }
}
