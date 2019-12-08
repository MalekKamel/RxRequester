package com.rxrequester.app.presentation.rxrequester

import com.rxrequester.app.R
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.exception.handler.throwable.ThrowableHandler
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

    override fun handle(throwable: Throwable, presentable: Presentable) {

        if (throwable is SocketTimeoutException) {
            presentable.showError(R.string.socket_timeout_exception)
            return
        }

        if (throwable is UnknownHostException) {
            presentable.showError(R.string.offline_internet)
            return
        }

        if (throwable is ConnectionShutdownException) {
            presentable.showError(R.string.socket_timeout_exception)
            return
        }

        presentable.showError(R.string.offline_internet)
    }
}
