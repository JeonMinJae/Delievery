package mj.project.delievery.data.repository.user

import mj.project.delievery.data.entity.locaion.LocationLatLngEntity

interface UserRepository {

    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity)


}
