package mj.project.delievery.screen.main.home.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.repository.restaurant.RestaurantRepository
import mj.project.delievery.model.restaurant.RestaurantModel
import mj.project.delievery.screen.base.BaseViewModel

class RestaurantListViewModel(
    private val restaurantCategory: RestaurantCategory,
    private var locationLatLng: LocationLatLngEntity,
    private val restaurantRepository: RestaurantRepository,
    private var restaurantFilterOrder: RestautantFilterOrder = RestautantFilterOrder.DEFAULT


) : BaseViewModel() {

    val restaurantListLiveData= MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        val restaurantList = restaurantRepository.getList(restaurantCategory, locationLatLng)
        val sortedList = when (restaurantFilterOrder) {
            RestautantFilterOrder.DEFAULT -> {
                restaurantList
            }
            RestautantFilterOrder.LOW_DELIVERY_TIP -> {
                restaurantList.sortedBy { it.deliveryTipRange.first }
            }
            RestautantFilterOrder.FAST_DELIVERY -> {
                restaurantList.sortedBy { it.deliveryTimeRange.first }
            }
            RestautantFilterOrder.TOP_RATE -> {
                restaurantList.sortedByDescending { it.grade }
            }
        }
        restaurantListLiveData.value = sortedList.map {
            RestaurantModel(
                id = it.id,
                restaurantInfoId = it.restaurantInfoId,
                restaurantCategory = it.restaurantCategory,
                restaurantTitle = it.restaurantTitle,
                restaurantImageUrl = it.restaurantImageUrl,
                grade = it.grade,
                reviewCount = it.reviewCount,
                deliveryTimeRange = it.deliveryTimeRange,
                deliveryTipRange = it.deliveryTipRange,
                restaurantTelNumber = it.restaurantTelNumber
            ) // 오류가 뜨면 CELLTYPE의 기본값을 안 정해줘서다
        }
    }

    fun setLocationLatLng(locationLatLng: LocationLatLngEntity) {
        this.locationLatLng = locationLatLng
        fetchData()
    }

    fun setRestaurantFilterOrder(order: RestautantFilterOrder) {
        this.restaurantFilterOrder = order
        fetchData()
    }
}
