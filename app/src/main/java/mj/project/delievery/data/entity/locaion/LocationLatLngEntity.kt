package mj.project.delievery.data.entity.locaion

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import mj.project.delievery.data.entity.Entity

//위도 경도 입력 엔티티
@Parcelize
@androidx.room.Entity
data class LocationLatLngEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true) //자동 교체될것이다.
    override val id: Long = -1 //일반적으로 필요없어서 -1넣어줌 && 이 id를 맨처음 넣어주면 entity사용할때 에러가 뜰수있다.
): Entity, Parcelable
