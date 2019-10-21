package com.rxrequester.app.presentation.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxrequester.app.R
import com.rxrequester.app.data.DataManager
import com.rxrequester.app.presentation.rxrequester.*
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import com.rxrequester.app.presentation.rxrequester.TokenExpiredHandler
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(val dm: DataManager)
    : ViewModel() {

    val disposables: CompositeDisposable = CompositeDisposable()
    var requester: RxRequester

    val toggleLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<String>()
    val showErrorRes = MutableLiveData<Int>()

    init {
        requester = setupRequester()
    }

    private fun setupRequester(): RxRequester {

        if (RxRequester.throwableHandlers.isEmpty()) {
            RxRequester.resumableHandlers = listOf(TokenExpiredHandler())
            RxRequester.httpHandlers = listOf(ServerErrorHandler())
            RxRequester.throwableHandlers = listOf(
                    IoExceptionHandler(),
                    NoSuchElementHandler(),
                    OutOfMemoryErrorHandler()
            )
        }

        val presentable = object: Presentable {
            override fun showError(error: String) { showError.value = error }
            override fun showError(error: Int) { showErrorRes.value = error }
            override fun showLoading() { toggleLoading.value = true }
            override fun hideLoading() { toggleLoading.value = false }
            override fun onHandleErrorFailed(throwable: Throwable) { showErrorRes.value = R.string.oops_something_went_wrong }
        }

        return RxRequester.create(ErrorContract::class.java, presentable)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}

