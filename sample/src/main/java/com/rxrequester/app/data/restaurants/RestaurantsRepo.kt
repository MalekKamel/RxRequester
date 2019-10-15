package com.rxrequester.app.data.restaurants

import io.reactivex.Flowable
import com.rxrequester.app.data.model.RestaurantResponse

class RestaurantsRepo(private val restaurantDataSrc: RestaurantDataSrc) {

    fun all(): Flowable<RestaurantResponse> {
        return restaurantDataSrc.all()
    }


}
