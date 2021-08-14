package mj.project.delievery.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mj.project.delievery.R
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.data.repository.map.MapRepository
import mj.project.delievery.data.repository.restaurant.food.RestaurantFoodRepository
import mj.project.delievery.data.repository.user.UserRepository
import mj.project.delievery.screen.base.BaseViewModel

class HomeViewModel(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    private val restaurantFoodRepository: RestaurantFoodRepository
): BaseViewModel() {

    companion object {
        const val MY_LOCATION_KEY = "MyLocation"
    }

    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.Uninitialized)

    val foodMenuBasketLiveData = MutableLiveData<List<RestaurantFoodEntity>>()

    //api호출
    fun loadReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    )= viewModelScope.launch {
        homeStateLiveData.value = HomeState.Loading
        val userLocation = userRepository.getUserLocation() //현재 저장된 location가져옴
        val currentLocation = userLocation ?: locationLatLngEntity //userLocation을 가져오고 없으면 latlng가 현재위치
        val addressInfo = mapRepository.getReverseGeoInformation(locationLatLngEntity)
        addressInfo?.let { info ->
            homeStateLiveData.value = HomeState.Success(
                mapSearchInfo = info.toSearchInfoEntity(locationLatLngEntity),
                isLocationSame = currentLocation == locationLatLngEntity //내 위치가 gps기반으로 하는 위치가 맞느냐
            )
        } ?: kotlin.run {
            homeStateLiveData.value = HomeState.Error(
                R.string.can_not_load_address_info
            )
        }

    }

    // 받아둔 데이터있는지 판별
    fun getMapSearchInfo(): MapSearchInfoEntity? {
        when (val data = homeStateLiveData.value) {
            is HomeState.Success -> {
                return data.mapSearchInfo
            }
        }
        return null
    }

    fun checkMyBasket() = viewModelScope.launch {
        foodMenuBasketLiveData.value = restaurantFoodRepository.getAllFoodMenuListInBasket()
    }


}