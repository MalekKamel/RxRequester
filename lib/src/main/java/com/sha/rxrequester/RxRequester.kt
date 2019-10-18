package com.sha.rxrequester

import com.sha.rxrequester.exception.ErrorMessage
import com.sha.rxrequester.exception.InterceptorArgs
import com.sha.rxrequester.exception.RxExceptionInterceptor
import com.sha.rxrequester.exception.handler.http.HttpExceptionHandler
import com.sha.rxrequester.exception.handler.throwable.ThrowableHandler
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor

typealias Request<T> = () -> Flowable<T>

class RxRequester private constructor(
        private val serverErrorContract: Class<*>?,
        private val presentable: Presentable
){
    private val disposables: CompositeDisposable = CompositeDisposable()

    companion object {
        @JvmStatic
        var httpHandlers = listOf<HttpExceptionHandler>()
        @JvmStatic
        var throwableHandlers = listOf<ThrowableHandler<*>>()

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
    }

    /**
     * utility method to support Java overloading
     */
    fun <T> request(request: Request<T>): Flowable<T> {
        return request(RequestOptions.defaultInfo(), request)
    }

    /**
     * this function creates a new PublishProcessor and return it to the caller
     * then runs doRequest() which runs the request and publishes the result
     * to PublishProcessor.
     * @param requestOptions options for calling the request.
     * @param request callback for the request.
     */
    fun <T> request(
            requestOptions: RequestOptions = RequestOptions.defaultInfo(),
            request: Request<T>
    ): Flowable<T> {
        if (requestOptions.showLoading)
            presentable.showLoading()

        val ps = PublishProcessor.create<T>()
        try {
            return ps
        } finally {
            doRequest(requestOptions, request, ps)
        }
    }

    /**
     * this function runs the request and publishes the result to PublishProcessor which in turn
     * returns the result to the caller in case of success.
     * If any error occurred, it will be handled by handler classes.
     * @param requestOptions options for calling the request
     * @param request callback for the request.
     * @param ps PublishProcessor to be called after success.
     */
    private fun <T> doRequest(
            requestOptions: RequestOptions,
            request: Request<T>,
            ps: PublishProcessor<T>
    ) {
        val args = InterceptorArgs(
                requester = this,
                presentable = presentable,
                serverErrorContract = serverErrorContract,
                inlineHandling = requestOptions.inlineHandling,
                retryRequest = { doRequest(requestOptions, request, ps) }
        )

        Flowable.fromPublisher(request())
                .subscribeOn(requestOptions.subscribeOnScheduler)
                .observeOn(requestOptions.observeOnScheduler)
                .doOnError(RxExceptionInterceptor(args))
                .onErrorResumeNext(Flowable.empty<T>())
                .subscribe {
                    ps.onNext(it)
                    ps.onComplete()
                    presentable.hideLoading()
                }.disposeBy(disposables)
    }

    fun dispose() {
        disposables.dispose()
    }
}

fun Disposable.disposeBy(disposable: CompositeDisposable) {
    disposable.add(this)
}