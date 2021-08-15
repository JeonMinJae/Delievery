package mj.project.delievery.data.entity.restaurant

import android.net.Uri
import mj.project.delievery.data.entity.Entity

data class RestaurantReviewEntity(
    override val id: Long,
    val title: String,
    val description: String,
    val grade: Int,
    val images: List<Uri>? = null
): Entity
