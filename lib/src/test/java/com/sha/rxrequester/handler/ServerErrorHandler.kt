package com.sha.rxrequester.handler

import com.sha.rxrequester.exception.handler.http.HttpExceptionHandler
import com.sha.rxrequester.exception.handler.http.HttpExceptionInfo


class ServerErrorHandler : HttpExceptionHandler() {

    override fun supportedErrors(): List<Int> {
        return listOf(500)
    }

    override fun handle(info: HttpExceptionInfo) {
        info.presentable.showError("500 server error")
    }

}