package mj.project.delievery.data.entity.locaion

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import mj.project.delievery.data.entity.Entity

//위도 경도 입력 엔티티
@Parcelize // 보일러플레이트(변경 없이 재사용 가능한 프로그램)를 자동으로 생성
@androidx.room.Entity  //  해당 클래스가 Room데이터 베이스의 Entity임을 나타내는 annotaion이다.
data class LocationLatLngEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true) //Room에서 항목에 자동 ID를 할당
    override val id: Long = -1 //일반적으로 필요없어서 -1넣어줌 && 이 id를 맨처음 넣어주면 entity사용할때 에러가 뜰수있다.
): Entity, Parcelable  // Parcelable은 데이터 전달 할 수 있도록 해준다.
