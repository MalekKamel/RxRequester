package com.sha.rxrequester

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sha.rxrequester.handler.OutOfMemoryErrorHandler
import com.sha.rxrequester.handler.TokenExpiredHandler
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


class RxRequesterTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var presentable: Presentable
    lateinit var rxRequester: RxRequester

    @Before
    fun setup() {
        presentable = mock()
        rxRequester = RxRequester.create(presentable)

        RxRequester.throwableHandlers = listOf(OutOfMemoryErrorHandler())
        RxRequester.resumableHandlers = listOf(TokenExpiredHandler())

        RxRequester.defaultSchedulerProvider = TestSchedulerProvider
    }

    @Test
    fun request_succeedsAndLoadingToggles() {
        rxRequester.request { Flowable.just(Foo(foo = "foo")) }
                .test()
                .assertNoErrors()
                .assertValueAt(0, Foo(foo = "foo"))
                .assertValueCount(1)

        verify(presentable).showLoading()
        verify(presentable).hideLoading()
    }

    @Test
    fun request_throw401Exception() {
        val body = ResponseBody.create(MediaType.parse("text/plain"), "content")
        val response: Response<Int> = Response.error(401, body)
        val httpException = HttpException(response)

        rxRequester.request { Flowable.error<Foo>(httpException) }
                .test()
                .assertNoErrors()
    }

    @Test
    fun request_throwUnknownException() {
        val body = ResponseBody.create(MediaType.parse("text/plain"), "content")
        val response: Response<Int> = Response.error(403, body)
        val httpException = HttpException(response)

        rxRequester.request { Flowable.error<Foo>(httpException) }
                .test()
                .assertNoErrors()
        verify(presentable).onHandleErrorFailed(httpException)
    }

}

data class Foo(val foo: String)

