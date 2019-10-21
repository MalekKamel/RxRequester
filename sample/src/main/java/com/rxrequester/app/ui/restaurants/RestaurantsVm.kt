package com.rxrequester.app.ui.restaurants

import com.rxrequester.app.data.DataManager
import com.rxrequester.app.data.mapper.ListMapperImpl
import com.rxrequester.app.data.model.Restaurant
import com.rxrequester.app.data.model.RestaurantMapper
import com.rxrequester.app.presentation.view.BaseViewModel
import com.rxrequester.app.util.disposeBy
import com.sha.rxrequester.RequestOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restaurantsModule = module {
    viewModel { RestaurantsVm(get()) }
}

class RestaurantsVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    fun restaurants(callback: (List<Restaurant>) -> Unit) {
        val requestInfo = RequestOptions.Builder()
                .inlineErrorHandling { false }
                .showLoading(true)
                .subscribeOnScheduler(Schedulers.io())
                .observeOnScheduler(AndroidSchedulers.mainThread())
                .build()
        requester.request(requestInfo) { dm.restaurantsRepo.all() }
                .subscribe {
                    val list =  ListMapperImpl(RestaurantMapper()).map(it.restaurants)
                    callback(list)
                }.disposeBy(disposable = disposables)
    }

}

