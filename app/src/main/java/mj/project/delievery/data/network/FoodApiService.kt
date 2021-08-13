package mj.project.delievery.data.network

import mj.project.delievery.data.response.restaurant.RestaurantFoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiService {
    //restaurantId 가 1 이면 식당id를 1로가진 음식들을 보여주려한다.
    @GET("restaurants/{restaurantId}/foods")
    suspend fun getRestaurantFoods(
        @Path("restaurantId") restaurantId: Long
    ): Response<List<RestaurantFoodResponse>>

}