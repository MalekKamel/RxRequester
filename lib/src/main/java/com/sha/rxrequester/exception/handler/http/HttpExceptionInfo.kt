package com.sha.rxrequester.exception.handler.http

import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester


data class HttpExceptionInfo (
        val throwable: Throwable,
        val presentable: Presentable,
        val requester: RxRequester,
        val errorBody: String,
        val code: Int
)
