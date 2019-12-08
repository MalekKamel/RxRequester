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
    lateinit var interceptor: ExceptionInterceptor

    @Before
    fun setup() {
        presentable = mock()
        rxRequester = RxRequester.create(presentable)
        RxRequester.throwableHandlers = listOf(OutOfMemoryErrorHandler())

        val args = InterceptorArgs(
                presentable = presentable,
                serverErrorContract = null,
                inlineHandling = { false }
        )
        interceptor = ExceptionInterceptor(args)
    }

    @Test
    fun accept_outOfMemoryError() {
        interceptor.accept(OutOfMemoryError())
        verify(presentable).showError("OutOfMemoryError")
    }

    @Test
    fun accept_inlineHandlingIsNull() {
        interceptor.accept(OutOfMemoryError())

        verify(presentable).showError("OutOfMemoryError")
    }

    @Test
    fun accept_invokeRetryRequest() {
        interceptor.accept(OutOfMemoryError())

        verify(presentable).showError("OutOfMemoryError")
    }

    @Test
    fun accept_inlineHandlingReturnTrue() {
        var isInlineHandlingInvoked = false
        val args = InterceptorArgs(
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