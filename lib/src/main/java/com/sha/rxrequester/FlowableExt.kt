package com.sha.rxrequester

import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo
import io.reactivex.Flowable
import io.reactivex.functions.Function

internal fun <T> Flowable<T>.onErrorHandleResumable(
        toBeResumed: Flowable<T>,
        presentable: Presentable,
        requester: RxRequester
): Flowable<T> {
    return onErrorResumeNext(Function { throwable ->
        val info = ThrowableInfo(
                throwable = throwable,
                presentable = presentable,
                requester = requester
        )

        val handler = RxRequester.resumableHandlers.firstOrNull {
            it.canHandle(info)
        } ?: return@Function Flowable.error(throwable)

        return@Function handler.handle(info).flatMap {
            return@flatMap toBeResumed
        }
    })
}