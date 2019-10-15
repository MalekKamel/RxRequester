package com.restaurants.app.data.restaurants

import io.reactivex.Flowable
import com.restaurants.app.data.model.RestaurantResponse
import com.restaurants.app.data.api.ApiInterface

class RestaurantDataSrc(private val api: ApiInterface) {

    fun all(): Flowable<RestaurantResponse> {
        return api.restaurants()
    }

}