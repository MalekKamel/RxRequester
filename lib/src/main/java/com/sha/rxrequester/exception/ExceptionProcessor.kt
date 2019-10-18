package com.sha.rxrequester.exception

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import com.sha.rxrequester.exception.handler.http.HttpExceptionInfo
import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo
import retrofit2.HttpException

/**
 * Created by Sha on 10/9/17.
 */

internal object ExceptionProcessor {

    fun process(
            throwable: Throwable,
            presentable: Presentable,
            serverErrorContract: Class<*>,
            retryRequest: () -> Unit,
            requester: RxRequester
            ) {
        try {

            if (throwable is HttpException) {
                handleHttpException(throwable, retryRequest, serverErrorContract, presentable, requester)
                return
            }

            handleThrowable(throwable, retryRequest, presentable, requester)

        } catch (e: Exception) {
            e.printStackTrace()
            // Retrofit throws an exception
            unknownException(presentable, throwable)
        }

    }

    private fun handleHttpException(
            throwable: Throwable,
            retryRequest: () -> Unit,
            serverErrorContract: Class<*>,
            presentable: Presentable,
            requester: RxRequester
    ) {
        val httpException = throwable as HttpException

        val body = httpException.error()
        val code = httpException.errorCode()

        if (code == null) {
            unknownException(presentable, throwable)
            return
        }

        val optHandler = RxRequester.httpHandlers.firstOrNull { it.canHandle(code) }

        if (optHandler == null) {
            showOriginalHttpMessage(body, presentable, throwable, serverErrorContract)
            return
        }

        val info = HttpExceptionInfo(
                throwable = throwable,
                presentable = presentable,
                retryRequest = retryRequest,
                requester = requester,
                errorBody = body,
                code = code
        )

        optHandler.handle(info)

    }

    private fun showOriginalHttpMessage(
            body: String,
            presentable: Presentable,
            throwable: Throwable,
            serverErrorContract: Class<*>
    ) {
        val contract = parseErrorContract(body, serverErrorContract)

        if (TextUtils.isEmpty(contract.errorMessage())) {
            unknownException(presentable, throwable)
            return
        }

        presentable.showError(contract.errorMessage())
    }

    private fun handleThrowable(
            throwable: Throwable,
            retryRequest: () -> Unit,
            presentable: Presentable,
            requester: RxRequester
    ) {
        val optHandler = RxRequester.nonHttpHandlers.firstOrNull { it.canHandle(throwable) }

        if (optHandler == null) {
            unknownException(presentable, throwable)
            return
        }

        val info = ThrowableInfo(
                throwable = throwable,
                presentable = presentable,
                retryRequest = retryRequest,
                requester = requester
                )

        optHandler.handle(info)
    }

    private fun unknownException(presentable: Presentable, throwable: Throwable) {
        // Default handling, show generic problem.
        presentable.onHandleErrorFailed()
    }

    private fun parseErrorContract(body: String, serverErrorContract: Class<*>): ErrorMessage {
        return GsonBuilder().create().fromJson(body, serverErrorContract) as ErrorMessage
    }

}
