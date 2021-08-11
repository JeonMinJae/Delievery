package mj.project.delievery.screen.main.home.restaurant.detail

import mj.project.delievery.data.entity.restaurant.RestaurantEntity

sealed class RestaurantDetailState {

    object Uninitialized: RestaurantDetailState()

    object Loading: RestaurantDetailState()

    data class Success(
        val restaurantEntity: RestaurantEntity,
        val isLiked: Boolean? =null
    ): RestaurantDetailState()

}
