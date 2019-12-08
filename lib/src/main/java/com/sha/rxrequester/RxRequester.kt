package com.sha.rxrequester

import com.sha.rxrequester.exception.ErrorMessage
import com.sha.rxrequester.exception.ExceptionInterceptor
import com.sha.rxrequester.exception.InterceptorArgs
import com.sha.rxrequester.exception.handler.http.HttpExceptionHandler
import com.sha.rxrequester.exception.handler.resumable.ResumableHandler
import com.sha.rxrequester.exception.handler.throwable.ThrowableHandler
import io.reactivex.Flowable

typealias Request<T> = () -> Flowable<T>

class RxRequester private constructor(
        private val serverErrorContract: Class<*>?,
        private val presentable: Presentable
){
    companion object {
        @JvmStatic
        var defaultSchedulerProvider: SchedulerProvider? = null

        @JvmStatic
        var httpHandlers = listOf<HttpExceptionHandler>()
        @JvmStatic
        var throwableHandlers = listOf<ThrowableHandler<*>>()
        @JvmStatic
        var resumableHandlers = listOf<ResumableHandler>()

        /**
         * create requester instance
         */
        fun <T: ErrorMessage> create(
                serverErrorContract: Class<T>? = null,
                presentable: Presentable): RxRequester {
            return RxRequester(serverErrorContract, presentable)
        }

        /**
         * utility to support Java overloading
         */
        fun create(presentable: Presentable): RxRequester {
            return create<ErrorMessage>(null, presentable)
        }

        fun create(presentable: Presentable, block: Builder.() -> Unit): RxRequester {
            val builder = Builder().apply { block() }
            httpHandlers = builder.httpHandlers
            throwableHandlers = builder.throwableHandlers
            resumableHandlers = builder.resumableHandlers
            return RxRequester(builder.serverErrorContract, presentable)
        }

        class Builder {
            var httpHandlers = listOf<HttpExceptionHandler>()
            var throwableHandlers = listOf<ThrowableHandler<*>>()
            var resumableHandlers = listOf<ResumableHandler>()
            var serverErrorContract: Class<*>? = null
        }

    }

    /**
     * wraps an RxJava request to apply schedulers, error handlers, and
     * request options.
     * @param requestOptions options for calling the request.
     * @param request callback for the request.
     */
    @JvmOverloads
    fun <T> request(
            requestOptions: RequestOptions = RequestOptions.defaultOptions(),
            request: Request<T>
    ): Flowable<T> {

        if (requestOptions.showLoading) presentable.showLoading()

        val args = InterceptorArgs(
                presentable = presentable,
                serverErrorContract = serverErrorContract,
                inlineHandling = requestOptions.inlineHandling
        )

        return request()
                .subscribeOn(requestOptions.subscribeOnScheduler())
                .observeOn(requestOptions.observeOnScheduler())
                .onErrorHandleResumable(request(), presentable, requestOptions)
                .doOnError(ExceptionInterceptor(args))
                .onErrorResumeNext(Flowable.empty<T>())
                .observeOn(requestOptions.observeOnScheduler())
                .doOnNext{ if (requestOptions.showLoading) presentable.hideLoading() }
    }

}