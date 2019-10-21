package com.sha.rxrequester

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sha.rxrequester.exception.InterceptorArgs
import com.sha.rxrequester.exception.ExceptionInterceptor
import com.sha.rxrequester.handler.OutOfMemoryErrorHandler
import org.junit.Before
import org.junit.Test

class ExceptionInterceptorTest {
    private lateinit var presentable: Presentable
    lateinit var rxRequester: RxRequester

    @Before
    fun setup() {
        presentable = mock()
        rxRequester = RxRequester.create(presentable)
        RxRequester.throwableHandlers = listOf(OutOfMemoryErrorHandler())
    }

    @Test
    fun accept_outOfMemoryError() {
        val args = InterceptorArgs(
                requester = rxRequester,
                presentable = presentable,
                serverErrorContract = null,
                inlineHandling = { false }
        )
        ExceptionInterceptor(args).accept(OutOfMemoryError())
        verify(presentable).showError("OutOfMemoryError")
    }

    @Test
    fun accept_inlineHandlingIsNull() {
        val args = InterceptorArgs(
                requester = rxRequester,
                presentable = presentable,
                serverErrorContract = null,
                inlineHandling = null
        )
        ExceptionInterceptor(args).accept(OutOfMemoryError())

        verify(presentable).showError("OutOfMemoryError")
    }

    @Test
    fun accept_invokeRetryRequest() {
        val args = InterceptorArgs(
                requester = rxRequester,
                presentable = presentable,
                serverErrorContract = null,
                inlineHandling = null
        )
        ExceptionInterceptor(args).accept(OutOfMemoryError())

        verify(presentable).showError("OutOfMemoryError")
    }

    @Test
    fun accept_inlineHandlingReturnTrue() {
        var isInlineHandlingInvoked = false
        val args = InterceptorArgs(
                requester = rxRequester,
                presentable = presentable,
                serverErrorContract = null,
                inlineHandling = {
                    isInlineHandlingInvoked = true
                    true
                }
        )
        ExceptionInterceptor(args).accept(OutOfMemoryError())

        assert(isInlineHandlingInvoked)
    }


}