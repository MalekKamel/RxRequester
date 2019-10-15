package com.rxrequester.app.data.restaurants

import io.reactivex.Flowable
import com.rxrequester.app.data.model.RestaurantResponse
import com.rxrequester.app.data.api.ApiInterface

class RestaurantDataSrc(private val api: ApiInterface) {

    fun all(): Flowable<RestaurantResponse> {
        return api.restaurants()
    }

}