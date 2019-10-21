package com.sha.rxrequester.exception.handler.throwable

import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import retrofit2.HttpException

data class ThrowableInfo(
        var throwable: Throwable,
        var presentable: Presentable,
        val requester: RxRequester
) {
    fun asHttpException(): HttpException? {
        return throwable as? HttpException
    }
}