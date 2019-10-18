package com.sha.rxrequester.exception.handler.throwable

import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester

data class ThrowableInfo(
        var throwable: Throwable,
        var presentable: Presentable,
        val retryRequest: () -> Unit,
        val requester: RxRequester
)