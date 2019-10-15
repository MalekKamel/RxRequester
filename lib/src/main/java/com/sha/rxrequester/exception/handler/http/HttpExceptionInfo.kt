package com.sha.rxrequester.exception.handler.http

import com.sha.rxrequester.Presentable


data class HttpExceptionInfo (
        val throwable: Throwable,
        val presentable: Presentable,
        val retryRequest: () -> Unit,
        val errorBody: String,
        val code: Int
)
