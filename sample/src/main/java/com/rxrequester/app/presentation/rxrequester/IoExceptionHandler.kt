package com.rxrequester.app.presentation.rxrequester

import com.rxrequester.app.R
import com.sha.rxrequester.exception.handler.throwable.ThrowableHandler
import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Sha on 10/9/17.
 */

class IoExceptionHandler : ThrowableHandler<IOException>() {

    override fun supportedErrors(): List<Class<out IOException>> {
        return listOf(IOException::class.java, SocketTimeoutException::class.java)
    }

    override fun handle(info: ThrowableInfo) {

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
