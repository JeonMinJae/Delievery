package mj.project.delievery.data.repository.map

import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.response.address.AddressInfo

interface MapRepository {

    suspend fun getReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ): AddressInfo?  //response-AddressInfo

}
