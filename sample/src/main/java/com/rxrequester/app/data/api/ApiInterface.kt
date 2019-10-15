package com.rxrequester.app.data.api

import io.reactivex.Flowable
import com.rxrequester.app.data.model.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("restaurants")
    fun restaurants(@Query("country") country: String = "US"): Flowable<RestaurantResponse>
}