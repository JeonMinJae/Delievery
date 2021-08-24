package mj.project.delievery.data.entity.order

import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity

data class OrderEntity(
    val id: String,
    val userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>
)
