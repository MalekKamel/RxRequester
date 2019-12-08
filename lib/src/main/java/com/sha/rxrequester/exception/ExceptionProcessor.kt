package com.sha.rxrequester.exception

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import retrofit2.HttpException

internal object ExceptionProcessor {

    fun process(
            throwable: Throwable,
            presentable: Presentable,
            serverErrorContract: Class<*>?
            ) {
        try {

            if (throwable is HttpException) {
                handleHttpException(throwable, serverErrorContract, presentable)
                return
            }

            handleThrowable(throwable, presentable)

        } catch (e: Exception) {
            e.printStackTrace()
            // Retrofit throws an exception
            uncaughtException(presentable, throwable)
        }

    }

    private fun handleHttpException(
            throwable: Throwable,
            serverErrorContract: Class<*>?,
            presentable: Presentable
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

        optHandler.handle(throwable, presentable, code, body)

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
            presentable: Presentable
    ) {
        val optHandler = RxRequester.throwableHandlers.firstOrNull { it.canHandle(throwable) }

        if (optHandler == null) {
            uncaughtException(presentable, throwable)
            return
        }

        optHandler.handle(throwable, presentable)
    }

    private fun uncaughtException(presentable: Presentable, throwable: Throwable) {
        // Default handling, show generic problem.
        presentable.onHandleErrorFailed(throwable)
    }

    private fun parseErrorContract(body: String, serverErrorContract: Class<*>): ErrorMessage {
        return GsonBuilder().create().fromJson(body, serverErrorContract) as ErrorMessage
    }

}
