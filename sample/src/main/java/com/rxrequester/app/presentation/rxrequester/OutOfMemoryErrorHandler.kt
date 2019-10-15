package com.rxrequester.app.presentation.rxrequester

import com.rxrequester.app.R
import com.sha.rxrequester.exception.handler.nonhttp.NonHttpExceptionHandler
import com.sha.rxrequester.exception.handler.nonhttp.NonHttpExceptionInfo

class OutOfMemoryErrorHandler : NonHttpExceptionHandler<OutOfMemoryError>() {

    override fun supportedThrowables(): List<Class<OutOfMemoryError>> {
        return listOf(OutOfMemoryError::class.java)
    }

    override fun handle(info: NonHttpExceptionInfo) {
        info.presentable.showError(R.string.no_memory_free_up_space)
    }
}
