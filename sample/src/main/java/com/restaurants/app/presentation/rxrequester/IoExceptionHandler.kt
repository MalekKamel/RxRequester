package com.restaurants.app.presentation.rxrequester

import com.restaurants.app.R
import com.sha.rxrequester.exception.handler.nonhttp.NonHttpExceptionHandler
import com.sha.rxrequester.exception.handler.nonhttp.NonHttpExceptionInfo
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Sha on 10/9/17.
 */

class IoExceptionHandler : NonHttpExceptionHandler<IOException>() {

    override fun supportedThrowables(): List<Class<out IOException>> {
        return listOf(IOException::class.java, SocketTimeoutException::class.java)
    }

    override fun handle(info: NonHttpExceptionInfo) {

        if (info.throwable is SocketTimeoutException) {
            info.presentable.showError(R.string.socket_timeout_exception)
            return
        }

        if (info.throwable is UnknownHostException) {
            info.presentable.showError(R.string.offline_internet)
            return
        }

        if (info.throwable is ConnectionShutdownException) {
            info.presentable.showError(R.string.socket_timeout_exception)
            return
        }

        info.presentable.showError(R.string.offline_internet)
    }
}
