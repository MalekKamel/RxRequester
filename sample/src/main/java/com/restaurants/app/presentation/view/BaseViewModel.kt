package com.restaurants.app.presentation.view

import androidx.lifecycle.ViewModel
import com.restaurants.app.data.DataManager
import com.restaurants.app.presentation.rxrequester.*
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(val dm: DataManager)
    : ViewModel() {

    val disposables: CompositeDisposable = CompositeDisposable()
    var requester: RxRequester

    init {
        requester = setupRequester()
    }

    private fun setupRequester(): RxRequester {
        val presentable = object: Presentable {
            override fun showError(error: String) {
//                view.showErrorInFlashBar(error)
            }

            override fun showError(error: Int) {
//                view.showErrorInFlashBar(error)
            }

            override fun showLoading() {
//                view.showLoading()
            }

            override fun hideLoading() {
//                view.hideLoading()
            }

            override fun onHandleErrorFailed() {
//                view.showErrorInFlashBar(R.string.oops_something_went_wrong)
            }

        }

       val requester = RxRequester.create(ErrorContract::class.java, presentable)

        if (RxRequester.nonHttpHandlers.isEmpty())
            RxRequester.nonHttpHandlers = listOf(
                    IoExceptionHandler(),
                    NoSuchElementHandler(),
                    OutOfMemoryErrorHandler()
            )
        if (RxRequester.httpHandlers.isEmpty())
            RxRequester.httpHandlers = listOf(
                    TokenExpiredHandler(),
                    ServerErrorHandler()
            )
        return requester
    }

    override fun onCleared() {
        disposables.dispose()
        requester.dispose()
        super.onCleared()
    }

}

