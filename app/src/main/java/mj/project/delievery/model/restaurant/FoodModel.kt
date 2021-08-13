package mj.project.delievery.model.restaurant

import mj.project.delievery.model.CellType
import mj.project.delievery.model.Model

data class FoodModel(
    override val id: Long,
    override val type: CellType = CellType.FOOD_CELL,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long
) : Model(id, type)