package mj.project.delievery.data.entity.locaion

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
// 경도위도 엔티티로부터 data를 받아서 mapsearch를 하는 엔티티
@Parcelize
data class MapSearchInfoEntity(
    val fullAddress: String,
    val name: String,
    val locationLatLng: LocationLatLngEntity

): Parcelable
