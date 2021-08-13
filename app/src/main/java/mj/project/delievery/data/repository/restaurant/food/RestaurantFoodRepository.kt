package mj.project.delievery.data.repository.restaurant.food

import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity


interface RestaurantFoodRepository {

    suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity>


}
