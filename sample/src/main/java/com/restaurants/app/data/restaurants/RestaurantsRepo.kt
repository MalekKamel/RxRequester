package com.restaurants.app.data.restaurants

import io.reactivex.Flowable
import com.restaurants.app.data.model.RestaurantResponse

class RestaurantsRepo(private val restaurantDataSrc: RestaurantDataSrc) {

    fun all(): Flowable<RestaurantResponse> {
        return restaurantDataSrc.all()
    }


}
