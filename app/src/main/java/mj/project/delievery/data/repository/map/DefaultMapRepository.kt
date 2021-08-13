package mj.project.delievery.data.repository.map

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.network.MapApiService
import mj.project.delievery.data.response.address.AddressInfo

class DefaultMapRepository(
    private val mapApiService: MapApiService,
    private val ioDispatcher: CoroutineDispatcher
): MapRepository {

    override suspend fun getReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo? = withContext(ioDispatcher) {
        val response = mapApiService.getReverseGeoCode(
            lat = locationLatLngEntity.latitude,
            lon = locationLatLngEntity.longitude
        )
        if (response.isSuccessful) { // mapApiService.getReverseGeoCode()가 성공적으로 실행되면
            response.body()?.addressInfo
        } else {
            null
        }
    }
}
