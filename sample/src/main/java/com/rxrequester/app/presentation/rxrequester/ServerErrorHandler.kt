package com.rxrequester.app.presentation.rxrequester

import com.rxrequester.app.R
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.exception.handler.http.HttpExceptionHandler


class ServerErrorHandler : HttpExceptionHandler() {

    override fun supportedErrors(): List<Int> {
        return listOf(500)
    }

    override fun handle(throwable: Throwable, presentable: Presentable, errorCode: Int, errorBody: String) {
        presentable.showError(R.string.oops_something_went_wrong)
    }
}