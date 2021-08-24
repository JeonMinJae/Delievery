package mj.project.delievery.model.order

import mj.project.delievery.data.entity.order.OrderEntity
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.model.CellType
import mj.project.delievery.model.Model

data class OrderModel(
    override val id: Long,
    override val type: CellType = CellType.ORDER_CELL,
    val orderId: String,
    val userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>
): Model(id, type) {

    fun toEntity() = OrderEntity(
        id = orderId,
        userId = userId,
        restaurantId = restaurantId,
        foodMenuList = foodMenuList
    )

}
