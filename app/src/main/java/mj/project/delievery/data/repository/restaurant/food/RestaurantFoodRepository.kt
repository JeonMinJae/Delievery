package mj.project.delievery.data.repository.restaurant.food

import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity


interface RestaurantFoodRepository {

    suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity>

    suspend fun getAllFoodMenuListInBasket(): List<RestaurantFoodEntity>

    suspend fun getFoodMenuListInBasket(restaurantId: Long): List<RestaurantFoodEntity>

    suspend fun insertFoodMenuInBasket(restaurantFoodEntity: RestaurantFoodEntity)

    suspend fun removeFoodMenuListInBasket(foodId: String)

    suspend fun clearFoodMenuListInBasket()


}
