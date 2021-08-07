package mj.project.delievery.screen.main.home.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.delievery.data.entity.RestaurantEntity
import mj.project.delievery.data.repository.RestaurantRepository
import mj.project.delievery.model.restaurant.RestaurantModel
import mj.project.delievery.screen.base.BaseViewModel

class RestaurantListViewModel(
    private val restaurantCategory: RestaurantCategory,
    private val restaurantRepository: RestaurantRepository
) : BaseViewModel() {

    val restaurantListLiveData= MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        val restaurantList = restaurantRepository.getList(restaurantCategory)
                restaurantListLiveData.value = restaurantList.map {
                    RestaurantModel(
                        id = it.id,
                        restaurantInfoId = it.restaurantInfoId,
                        restaurantCategory = it.restaurantCategory,
                        restaurantTitle = it.restaurantTitle,
                        restaurantImageUrl = it.restaurantImageUrl,
                        grade = it.grade,
                        reviewCount = it.reviewCount,
                        deliveryTimeRange = it.deliveryTimeRange,
                        deliveryTipRange = it.deliveryTipRange
                    ) // 오류가 뜨면 CELLTYPE의 기본값을 안정해줘서다
                }


    }
}
