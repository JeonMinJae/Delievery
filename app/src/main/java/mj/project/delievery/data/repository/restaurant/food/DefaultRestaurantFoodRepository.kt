package mj.project.delievery.data.repository.restaurant.food

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.data.network.FoodApiService

class DefaultRestaurantFoodRepository(
    private val foodApiService: FoodApiService,
    private val ioDispatcher: CoroutineDispatcher
): RestaurantFoodRepository {

    override suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity> = withContext(ioDispatcher) {
        val response = foodApiService.getRestaurantFoods(restaurantId)
        if (response.isSuccessful) {
            response.body()?.map { it.toEntity(restaurantId) } ?: listOf() //response-restaurantfood의 id,title등을 map안에 람다에 실행
            //만약 get실행시 1이란 restaurantId가 들어왔다면 id,title등이 식당 1에대한 엔티티를 실행할거다.
        } else {
            listOf()
        }
    }

}
