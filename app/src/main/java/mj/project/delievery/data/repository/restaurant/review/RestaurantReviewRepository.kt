package mj.project.delievery.data.repository.restaurant.review

import mj.project.delievery.data.entity.restaurant.RestaurantReviewEntity

interface RestaurantReviewRepository {

    //id는 api용도로 쓰고있어서  title을 사용해서 유니크하게 해줬다.
    suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity>

}
