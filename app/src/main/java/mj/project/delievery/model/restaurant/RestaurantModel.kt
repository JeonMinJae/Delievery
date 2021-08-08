package mj.project.delievery.model.restaurant

import mj.project.delievery.data.entity.restaurant.RestaurantEntity
import mj.project.delievery.model.CellType
import mj.project.delievery.model.Model
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory

// 내부 db로 부터 data를 받아서 엔티티화해서 repository와 상호작용한다.
data class RestaurantModel(
    override val id: Long,
    override val type: CellType= CellType.RESTAURANT_CELL,
    val restaurantInfoId: Long,
    val restaurantCategory: RestaurantCategory,
    val restaurantTitle: String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int, Int>,
    val deliveryTipRange: Pair<Int, Int>
) : Model(id, type) {

    fun toEntity() = RestaurantEntity(
        id,
        restaurantInfoId,
        restaurantCategory,
        restaurantTitle,
        restaurantImageUrl,
        grade,
        reviewCount,
        deliveryTimeRange,
        deliveryTipRange
    )

}
