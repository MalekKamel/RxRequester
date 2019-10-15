package com.sha.rxrequester.exception.handler.nonhttp

import com.sha.rxrequester.Presentable

data class NonHttpExceptionInfo(
        var throwable: Throwable,
        var presentable: Presentable,
        val retryRequest: () -> Unit
)