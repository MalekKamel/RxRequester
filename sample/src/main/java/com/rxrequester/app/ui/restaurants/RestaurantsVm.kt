package com.rxrequester.app.ui.restaurants

import com.rxrequester.app.presentation.view.BaseViewModel
import com.rxrequester.app.data.DataManager
import com.rxrequester.app.data.model.Restaurant
import com.rxrequester.app.data.model.toPresentation
import com.sha.rxrequester.RequestOptions
import com.rxrequester.app.util.disposeBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restaurantsModule = module {
    viewModel { RestaurantsVm(get()) }
}

class RestaurantsVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    fun restaurants(callback: (MutableList<Restaurant>) -> Unit) {
        val requestInfo = RequestOptions.Builder()
                .inlineErrorHandling { false }
                .showLoading(true)
                .subscribeOnScheduler(Schedulers.io())
                .observeOnScheduler(AndroidSchedulers.mainThread())
                .build()
        requester.request(requestInfo) { dm.restaurantsRepo.all() }
                .subscribe {
                    callback(it.restaurants.toPresentation())
                }.disposeBy(disposable = disposables)
    }

}

