package com.sha.rxrequester.exception.handler.resumable

import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo
import io.reactivex.Flowable

/**
 * Created by Sha on 10/9/17.
 */

abstract class ResumableHandler<T: Throwable> {

    abstract fun canHandle(info: ThrowableInfo): Boolean

    abstract fun handle(info: ThrowableInfo): Flowable<Any>

}
