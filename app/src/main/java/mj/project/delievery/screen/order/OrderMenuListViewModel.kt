package mj.project.delievery.screen.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.delievery.data.repository.restaurant.food.RestaurantFoodRepository
import mj.project.delievery.model.CellType
import mj.project.delievery.model.restaurant.FoodModel
import mj.project.delievery.screen.base.BaseViewModel

class OrderMenuListViewModel(
    private val restaurantFoodRepository: RestaurantFoodRepository
):BaseViewModel() {

    //private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    val orderMenuStateLiveData = MutableLiveData<OrderMenuState>(OrderMenuState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        orderMenuStateLiveData.value = OrderMenuState.Loading
        val foodMenuList = restaurantFoodRepository.getAllFoodMenuListInBasket()
        orderMenuStateLiveData.value = OrderMenuState.Success(
            foodMenuList.map {
                FoodModel(
                    id = it.hashCode().toLong(),
                    type = CellType.ORDER_FOOD_CELL,
                    title = it.title,
                    description = it.description,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    restaurantId = it.restaurantId,
                    foodId = it.id,

                )
            }
        )
    }

    fun orderMenu() {
        TODO("Not yet implemented")
    }

    fun clearOrderMenu() {
        TODO("Not yet implemented")
    }

    fun removeOrderMenu(model: FoodModel) {

    }
}