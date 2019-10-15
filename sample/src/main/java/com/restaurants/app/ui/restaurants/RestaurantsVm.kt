package com.restaurants.app.ui.restaurants

import com.restaurants.app.presentation.view.BaseViewModel
import com.restaurants.app.data.DataManager
import com.restaurants.app.data.model.Restaurant
import com.restaurants.app.data.model.toPresentation
import com.sha.rxrequester.RequestInfo
import com.restaurants.app.util.disposeBy
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restaurantsModule = module {
    viewModel { RestaurantsVm(get()) }
}

class RestaurantsVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    fun restaurants(callback: (MutableList<Restaurant>) -> Unit) {
        val requestInfo = RequestInfo.Builder()
                .showLoading(true)
                .inlineErrorHandling { false }
                .build()
        requester.request(requestInfo) { dm.restaurantsRepo.all() }
                .subscribe {
                    callback(it.restaurants.toPresentation())
                }.disposeBy(disposable = disposables)
    }

}

