package com.sha.rxrequester

import androidx.annotation.StringRes

interface Presentable {
    fun showError(error: String)
    fun showError(@StringRes error: Int)
    fun showLoading()
    fun hideLoading()
    fun onHandleErrorFailed()
}