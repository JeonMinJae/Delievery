package mj.project.delievery.data.network

import mj.project.delievery.data.response.address.AddressInfoResponse
import mj.project.delievery.data.response.search.SearchResponse
import mj.project.delievery.data.url.Key
import mj.project.delievery.data.url.Url
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MapApiService {

    //맵 주변
    @GET(Url.GET_TMAP_POIS_AROUND)
    suspend fun getSearchLocationAround(
        @Header("appKey") appKey: String = Key.TMAP_API,
        @Query("version") version: Int = 1,
        @Query("categories") categories: String? = null,
        @Query("callback") callback: String? = null,
        @Query("count") count: Int = 20,
        @Query("searchKeyword") keyword: String? = null,
        @Query("areaLLCode") areaLLCode: String? = null,
        @Query("areaLMCode") areaLMCode: String? = null,
        @Query("resCoordType") resCoordType: String? = null,
        @Query("searchType") searchType: String? = null,
        @Query("multiPoint") multiPoint: String? = null,
        @Query("searchtypCd") searchtypCd: String? = null,
        @Query("radius") radius: String? = null,
        @Query("reqCoordType") reqCoordType: String? = null,
        @Query("centerLon") centerLon: String? = null,
        @Query("centerLat") centerLat: String? = null
    ): Response<SearchResponse>

    //좌표를통해 주소를 구한다.
    @GET(Url.GET_TMAP_REVERSE_GEO_CODE)
    suspend fun getReverseGeoCode(
        @Header("appKey") appKey: String = Key.TMAP_API,
        @Query("version") version: Int = 1,
        @Query("callback") callback: String? = null,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("coordType") coordType: String? = null, // 받고자 하는 응답 좌표계 유형 ex) WGS84(경위도), TM128(메카토르)
        @Query("addressType") addressType: String? = null
    ): Response<AddressInfoResponse>
}
