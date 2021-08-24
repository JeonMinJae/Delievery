package mj.project.delievery.data.repository.order

import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity

interface OrderRepository {

    suspend fun orderMenu(
        userId: String,  //로그인한 id
        restaurantId: Long,   // 식당id
        foodMenuList: List<RestaurantFoodEntity> // 식당 메뉴,설명,가격,사진
    ): DefaultOrderRepository.Result

    suspend fun getAllOrderMenus(
        userId: String
    ): DefaultOrderRepository.Result

}
