package mj.project.delievery.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity

@Dao
interface FoodMenuBasketDao {

    // 컴파일때 ":매개변수(바인드매개변수)"를 메서드 매개변수와 일치시킨다.
    @Query("SELECT * FROM RestaurantFoodEntity WHERE id=:foodId AND restaurantId=:restaurantId")
    suspend fun get(restaurantId: Long, foodId: Long): RestaurantFoodEntity?

    @Query("SELECT * FROM RestaurantFoodEntity")
    suspend fun getAll(): List<RestaurantFoodEntity>

    @Query("SELECT * FROM RestaurantFoodEntity WHERE restaurantId=:restaurantId")
    suspend fun getAllByRestaurantId(restaurantId: Long): List<RestaurantFoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurantFoodEntity: RestaurantFoodEntity)

    @Query("DELETE FROM RestaurantFoodEntity WHERE id=:foodId")
    suspend fun delete(foodId: String)

    @Query("DELETE FROM RestaurantFoodEntity")
    suspend fun deleteAll()

}
