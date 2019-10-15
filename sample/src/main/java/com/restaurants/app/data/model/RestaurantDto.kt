package com.restaurants.app.data.model

import com.google.gson.annotations.SerializedName

data class RestaurantDto(
        var name: String,
        var city: String,

        @SerializedName(value = "image_url")
        var poster: String

) {
        fun toRestaurant(): Restaurant {
                return Restaurant(
                        name = name,
                        city = city,
                        poster = poster
                )
        }
}

fun List<RestaurantDto>.toPresentation(): MutableList<Restaurant> {
        return map { it.toRestaurant() }.toMutableList()
}