package mj.project.delievery.data.response.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity

data class AddressInfo(
    @SerializedName("fullAddress")
    @Expose
    val fullAddress: String?, //완전히 보여지는 주소정보들

    @SerializedName("addressType")
    @Expose
    val addressType: String?,  //지번, 도로명+지번

    @SerializedName("city_do")
    @Expose
    val cityDo: String?,  //경기도

    @SerializedName("gu_gun")
    @Expose
    val guGun: String?,   //ㅇㅇ군

    @SerializedName("eup_myun")
    @Expose
    val eupMyun: String?,  //ㅇㅇ면

    @SerializedName("adminDong")
    @Expose
    val adminDong: String?, //행정동

    @SerializedName("adminDongCode")
    @Expose
    val adminDongCode: String?,

    @SerializedName("legalDong")
    @Expose
    val legalDong: String?,  //법정동

    @SerializedName("legalDongCode")
    @Expose
    val legalDongCode: String?,  //동 code

    @SerializedName("ri")
    @Expose
    val ri: String?, //ㅇㅇ리

    @SerializedName("bunji")
    @Expose
    val bunji: String?,  //ㅇㅇ번지

    @SerializedName("roadName")
    @Expose
    val roadName: String?,  //ㅇㅇ길

    @SerializedName("buildingIndex")
    @Expose
    val buildingIndex: String?,  //ㅇㅇ빌딩index

    @SerializedName("buildingName")
    @Expose
    val buildingName: String?,  //ㅇㅇ빌딩

    @SerializedName("mappingDistance")
    @Expose
    val mappingDistance: String?, //거리

    @SerializedName("roadCode")
    @Expose
    val roadCode: String?  //길code
){
    fun toSearchInfoEntity(locationLatLngEntity: LocationLatLngEntity) = MapSearchInfoEntity(
        fullAddress = fullAddress ?: "주소 정보 없음",
        name = buildingName ?: "빌딩정보 없음",
        locationLatLng = locationLatLngEntity  //외부에서 가져온 정보

    )
}
