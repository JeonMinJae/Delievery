package mj.project.delievery.screen.main.home.restaurant.detail.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.data.repository.restaurant.food.RestaurantFoodRepository
import mj.project.delievery.model.restaurant.FoodModel
import mj.project.delievery.screen.base.BaseViewModel

class RestaurantMenuListViewModel(
    private val restaurantId: Long,
    private val foodEntityList: List<RestaurantFoodEntity>
) : BaseViewModel() {

    val restaurantMenuListLiveData = MutableLiveData<List<FoodModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantMenuListLiveData.value = foodEntityList.map {
            FoodModel( //index0부터 들어오기때문에 순서바꾸면 안된다.  (foodmodel.value = RestaurantFoodEntity.value) 엔티티값을 model에 주입
                id = it.hashCode().toLong(),  // hashcode는 객체의 주소값을 변환하여 생성한 객체의 고유한 정수값이다.
                title = it.title,
                description = it.description,
                price = it.price,
                imageUrl = it.imageUrl,
                restaurantId = restaurantId
            )
        }
    }


}
