package mj.project.delievery.data.repository

import mj.project.delievery.data.entity.RestaurantEntity
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory

interface RestaurantRepository {

    //코루틴으로 구현할거기 때문에 suspend사용
    suspend fun getList(
        restaurantCategory: RestaurantCategory
    ): List<RestaurantEntity>

}
