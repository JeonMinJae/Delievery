package mj.project.delievery.data.entity.restaurant

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class RestaurantFoodEntity(
    @PrimaryKey val id: String,  // 음식 고유 id
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long  //식당 id ex)1 이면 restaurantId가1인 음식들을 보여주려한다.
): Parcelable