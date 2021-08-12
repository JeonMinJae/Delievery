package mj.project.delievery.data.repository.user

import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.restaurant.RestaurantEntity

interface UserRepository {

    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity)

    suspend fun getUserLikedRestaurant(restaurantTitle: String): RestaurantEntity?

    suspend fun insertUserLikedRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun deleteUserLikedRestaurant(restaurantTitle: String)

    suspend fun deleteALlUserLikedRestaurant()
}
