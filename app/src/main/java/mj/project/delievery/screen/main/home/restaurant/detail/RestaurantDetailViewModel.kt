package mj.project.delievery.screen.main.home.restaurant.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.delievery.data.entity.restaurant.RestaurantEntity
import mj.project.delievery.screen.base.BaseViewModel

class RestaurantDetailViewModel(
    private val restaurantEntity: RestaurantEntity
) : BaseViewModel() {

    val restaurantDetailStateLiveData = MutableLiveData<RestaurantDetailState>(RestaurantDetailState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity
        )
    }


}
