package com.sha.rxrequester.exception.handler.resumable

import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo
import io.reactivex.Flowable

abstract class ResumableHandler<T: Throwable> {

    /**
     * checks if the handler can handle the error
     * @return true if can handle
     */
    abstract fun canHandle(info: ThrowableInfo): Boolean

    /**
     * handle the error
     * @return Flowable that will be invoked after the error
     * and if it succeeds, the original request will be resumed again
     * For example, when receive 401 toke expired error, return the flowable that
     * refreshes the token here to be invoked and retry the original request again.
     */
    abstract fun handle(info: ThrowableInfo): Flowable<Any>

}
