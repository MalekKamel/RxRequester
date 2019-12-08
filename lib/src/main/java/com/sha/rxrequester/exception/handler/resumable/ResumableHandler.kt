package com.sha.rxrequester.exception.handler.resumable

import com.sha.rxrequester.Presentable
import io.reactivex.Flowable

abstract class ResumableHandler {

    /**
     * checks if the handler can handle the error
     * @return true if can handle
     */
    abstract fun canHandle(throwable: Throwable, presentable: Presentable): Boolean

    /**
     * handle the error
     * @return Flowable that will be invoked after the error
     * and if it succeeds, the original request will be resumed again
     * For example, when receive 401 toke expired error, return the flowable that
     * refreshes the token here to be invoked and retry the original request again.
     */
    abstract fun handle(throwable: Throwable, presentable: Presentable): Flowable<Any>

}
