package com.sha.rxrequester

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sha.rxrequester.exception.ExceptionProcessor
import com.sha.rxrequester.handler.OutOfMemoryErrorHandler
import com.sha.rxrequester.handler.ServerErrorHandler
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ExceptionProcessorTest {

    private lateinit var presentable: Presentable
    lateinit var rxRequester: RxRequester

    @Before
    fun setup() {
        presentable = mock()
        rxRequester = RxRequester.create(presentable)
        RxRequester.throwableHandlers = listOf(OutOfMemoryErrorHandler())
        RxRequester.httpHandlers = listOf(ServerErrorHandler())
    }

    @Test
    fun process_outOfMemory() {
        ExceptionProcessor.process(
                throwable = OutOfMemoryError(),
                presentable = presentable,
                serverErrorContract = null,
                requester = rxRequester
        )

        verify(presentable).showError("OutOfMemoryError")
    }

    @Test
    fun process_httpException() {

        val body = ResponseBody.create(MediaType.parse("text/plain"), "content")
        val response: Response<Int> = Response.error(500, body)
        val httpException = HttpException(response)

        ExceptionProcessor.process(
                throwable = httpException,
                presentable = presentable,
                serverErrorContract = null,
                requester = rxRequester
        )

        verify(presentable).showError("500 server error")

    }

    @Test
    fun process_handleErrorFailed() {

        val body = ResponseBody.create(MediaType.parse("text/plain"), "content")
        val response: Response<Int> = Response.error(402, body)
        val httpException = HttpException(response)

        ExceptionProcessor.process(
                throwable = httpException,
                presentable = presentable,
                serverErrorContract = null,
                requester = rxRequester
        )

        verify(presentable).onHandleErrorFailed(httpException)

    }
}

