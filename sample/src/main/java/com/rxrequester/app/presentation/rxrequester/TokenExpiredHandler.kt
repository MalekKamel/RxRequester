package com.rxrequester.app.presentation.rxrequester

import com.sha.rxrequester.exception.errorCode
import com.sha.rxrequester.exception.handler.resumable.ResumableHandler
import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo
import io.reactivex.Flowable

class TokenExpiredHandler: ResumableHandler() {

    override fun canHandle(info: ThrowableInfo): Boolean {
        return info.asHttpException()?.errorCode() == 401
    }

    override fun handle(info: ThrowableInfo): Flowable<Any> {
        return Flowable.just<Any>("Handled token expired!")
    }

}