package com.sha.rxrequester.exception

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import com.sha.rxrequester.exception.handler.http.HttpExceptionInfo
import com.sha.rxrequester.exception.handler.throwable.ThrowableInfo
import retrofit2.HttpException

internal object ExceptionProcessor {

    fun process(
            throwable: Throwable,
            presentable: Presentable,
            serverErrorContract: Class<*>?,
            requester: RxRequester
            ) {
        try {

            if (throwable is HttpException) {
                handleHttpException(throwable, serverErrorContract, presentable, requester)
                return
            }

            handleThrowable(throwable, presentable, requester)

        } catch (e: Exception) {
            e.printStackTrace()
            // Retrofit throws an exception
            uncaughtException(presentable, throwable)
        }

    }

    private fun handleHttpException(
            throwable: Throwable,
            serverErrorContract: Class<*>?,
            presentable: Presentable,
            requester: RxRequester
    ) {
        val httpException = throwable as HttpException

        val body = httpException.error()
        val code = httpException.errorCode()

        if (code == null) {
            uncaughtException(presentable, throwable)
            return
        }

        val optHandler = RxRequester.httpHandlers.firstOrNull { it.canHandle(code) }

        if (optHandler == null) {
            if (serverErrorContract != null)
                showOriginalHttpMessage(body, presentable, throwable, serverErrorContract)
            else
                uncaughtException(presentable, throwable)
            return
        }

        val info = HttpExceptionInfo(
                throwable = throwable,
                presentable = presentable,
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
            uncaughtException(presentable, throwable)
            return
        }

        presentable.showError(contract.errorMessage())
    }

    private fun handleThrowable(
            throwable: Throwable,
            presentable: Presentable,
            requester: RxRequester
    ) {
        val optHandler = RxRequester.throwableHandlers.firstOrNull { it.canHandle(throwable) }

        if (optHandler == null) {
            uncaughtException(presentable, throwable)
            return
        }

        val info = ThrowableInfo(
                throwable = throwable,
                presentable = presentable,
                requester = requester
                )

        optHandler.handle(info)
    }

    private fun uncaughtException(presentable: Presentable, throwable: Throwable) {
        // Default handling, show generic problem.
        presentable.onHandleErrorFailed(throwable)
    }

    private fun parseErrorContract(body: String, serverErrorContract: Class<*>): ErrorMessage {
        return GsonBuilder().create().fromJson(body, serverErrorContract) as ErrorMessage
    }

}
