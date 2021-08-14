package mj.project.delievery.model.restaurant

import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.model.CellType
import mj.project.delievery.model.Model

data class FoodModel(
    override val id: Long,
    override val type: CellType = CellType.FOOD_CELL,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long,
    val foodId: String
) : Model(id, type){
    fun toEntity(basketIndex: Int) = RestaurantFoodEntity(
        "${foodId}_${basketIndex}", title, description, price, imageUrl, restaurantId  //id가 선택한 foodId_총식당이 가진 푸드갯수 ex) 1_10, 2_10 ...
    )
}
