package mj.project.delievery.model.restaurant

import android.net.Uri
import mj.project.delievery.model.CellType
import mj.project.delievery.model.Model

data class RestaurantReviewModel(
    override val id: Long,
    override val type: CellType = CellType.REVIEW_CELL,
    val title: String,
    val description: String,
    val grade: Int,
    val thumbnailImageUri: Uri? = null
): Model(id, type)
