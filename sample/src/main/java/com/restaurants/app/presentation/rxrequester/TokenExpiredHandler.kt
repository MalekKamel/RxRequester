package com.restaurants.app.presentation.rxrequester

import com.sha.rxrequester.exception.handler.http.HttpExceptionHandler
import com.sha.rxrequester.exception.handler.http.HttpExceptionInfo

/**
 * Created by Sha on 10/9/17.
 */

class TokenExpiredHandler : HttpExceptionHandler() {

    override fun supportedExceptions(): List<Int> {
        return listOf(401)
    }

    override fun handle(info: HttpExceptionInfo) {
//        presenter.requester.request<*> { /* call refresh token request here */ }
//                .subscribe {
//                    presenter.retryRequest()
//                }
        info.retryRequest()
    }

}
