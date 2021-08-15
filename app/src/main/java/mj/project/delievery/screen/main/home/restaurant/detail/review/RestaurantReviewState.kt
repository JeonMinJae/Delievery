package mj.project.delievery.screen.main.home.restaurant.detail.review

import mj.project.delievery.data.entity.restaurant.RestaurantReviewEntity
import mj.project.delievery.model.restaurant.RestaurantReviewModel

sealed class RestaurantReviewState {

    object Uninitialized: RestaurantReviewState()

    object Loading: RestaurantReviewState()

    data class Success(
        val reviewList: List<RestaurantReviewModel>
    ): RestaurantReviewState()

}
