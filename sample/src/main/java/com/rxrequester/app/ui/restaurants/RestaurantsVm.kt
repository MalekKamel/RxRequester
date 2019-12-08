package com.rxrequester.app.ui.restaurants

import com.rxrequester.app.data.DataManager
import com.rxrequester.app.data.mapper.ListMapperImpl
import com.rxrequester.app.data.model.Restaurant
import com.rxrequester.app.data.model.RestaurantMapper
import com.rxrequester.app.presentation.view.BaseViewModel
import com.rxrequester.app.util.disposeBy
import com.sha.rxrequester.RequestOptions
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restaurantsModule = module {
    viewModel { RestaurantsVm(get()) }
}

class RestaurantsVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    fun restaurants(): Flowable<List<Restaurant>> {

        val options = RequestOptions.create {
            inlineHandling = { true }
            showLoading = true
            subscribeOnScheduler = Schedulers.io()
            observeOnScheduler = AndroidSchedulers.mainThread()
        }

        // OR (proper for Java)

        /*
        val options = RequestOptions.Builder()
                .inlineErrorHandling { false }
                .showLoading(true)
                .subscribeOnScheduler(Schedulers.io())
                .observeOnScheduler(AndroidSchedulers.mainThread())
                .build()
        */

       return requester.request(options) { dm.restaurantsRepo.all() }
                .map { ListMapperImpl(RestaurantMapper()).map(it.restaurants) }
    }

}

