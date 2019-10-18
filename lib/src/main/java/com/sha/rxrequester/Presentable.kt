package com.sha.rxrequester

import androidx.annotation.StringRes

interface Presentable {

    /**
     * show error string
     * @param error message string
     */
    fun showError(error: String)

    /**
     * show error resource
     * @param error message id
     */
    fun showError(@StringRes error: Int)

    /**
     * show loading indicator
     */
    fun showLoading()

    /**
     * hide loading indicator
     */
    fun hideLoading()

    /**
     * called when no error handler can handle the exception
     */
    fun onHandleErrorFailed(throwable: Throwable)
}