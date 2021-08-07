package mj.project.delievery.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mj.project.delievery.R
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity
import mj.project.delievery.data.repository.map.MapRepository
import mj.project.delievery.screen.base.BaseViewModel

class HomeViewModel(
    private val mapRepository: MapRepository
): BaseViewModel() {

    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.Uninitialized)

    //api호출
    fun loadReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    )= viewModelScope.launch {
        homeStateLiveData.value = HomeState.Loading

        val addressInfo = mapRepository.getReverseGeoInformation(locationLatLngEntity)
        addressInfo?.let { info ->
            homeStateLiveData.value = HomeState.Success(
                mapSearchInfo = info.toSearchInfoEntity(locationLatLngEntity)
            )
        } ?: kotlin.run {
            homeStateLiveData.value = HomeState.Error(
                R.string.can_not_load_address_info
            )
        }

    }


}