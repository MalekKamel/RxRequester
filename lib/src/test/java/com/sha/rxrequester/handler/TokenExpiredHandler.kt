package com.sha.rxrequester.handler

import com.sha.rxrequester.Presentable
import com.sha.rxrequester.exception.errorCode
import com.sha.rxrequester.exception.handler.resumable.ResumableHandler
import io.reactivex.Flowable
import retrofit2.HttpException

class TokenExpiredHandler: ResumableHandler() {

    override fun canHandle(throwable: Throwable, presentable: Presentable): Boolean {
        return (throwable as? HttpException)?.errorCode() == 401
    }

    override fun handle(throwable: Throwable, presentable: Presentable): Flowable<Any> {
        return Flowable.just<Any>("Handled 401")
    }

}