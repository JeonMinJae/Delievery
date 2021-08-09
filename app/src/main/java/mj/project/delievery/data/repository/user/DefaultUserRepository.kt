package mj.project.delievery.data.repository.user

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mj.project.delievery.data.db.dao.LocationDao
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity

class DefaultUserRepository(
    private val locationDao: LocationDao,
    private val ioDispatcher: CoroutineDispatcher
): UserRepository {

    override suspend fun getUserLocation(): LocationLatLngEntity? = withContext(ioDispatcher) {
        locationDao.get(-1)
    }

    override suspend fun insertUserLocation(
        locationLatLngEntity: LocationLatLngEntity
    ) = withContext(ioDispatcher) {
        locationDao.insert(locationLatLngEntity)
    }

}
