package com.sha.rxrequester

import io.reactivex.Flowable
import io.reactivex.functions.Function

internal fun <T> Flowable<T>.onErrorHandleResumable(
        toBeResumed: Flowable<T>,
        presentable: Presentable,
        requestOptions: RequestOptions): Flowable<T> {
    return onErrorResumeNext(Function { throwable ->
        if (requestOptions.inlineHandling?.invoke(throwable) == true)
            return@Function Flowable.empty()

        val handler = RxRequester.resumableHandlers.firstOrNull {
            it.canHandle(throwable, presentable)
        } ?: return@Function Flowable.error(throwable)

        return@Function handler.handle(throwable, presentable).flatMap { toBeResumed }
    })
}