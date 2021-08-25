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
    private val foodEntityList: List<RestaurantFoodEntity>,
    private val restaurantFoodRepository: RestaurantFoodRepository
) : BaseViewModel() {

    val restaurantMenuListLiveData = MutableLiveData<List<FoodModel>>()

    val menuBasketLiveData = MutableLiveData<RestaurantFoodEntity>()

    val isClearNeedInBasketLiveData = MutableLiveData<Pair<Boolean, () -> Unit>>()

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantMenuListLiveData.value = foodEntityList.map {
            FoodModel( //index0부터 들어오기때문에 순서바꾸면 안된다.
                id = it.hashCode().toLong(),  // hashcode는 객체의 주소값을 변환하여 생성한 객체의 고유한 정수값이다.
                title = it.title,
                description = it.description,
                price = it.price,
                imageUrl = it.imageUrl,
                restaurantId = restaurantId,
                foodId = it.id
            )
        }
    }

    // 메뉴를 장바구니에 담는 함수
    fun insertMenuInBasket(foodModel: FoodModel) = viewModelScope.launch {
        val restaurantMenuListInBasket = restaurantFoodRepository.getFoodMenuListInBasket(restaurantId)
        val foodMenuEntity = foodModel.toEntity(restaurantMenuListInBasket.size)
        val anotherRestaurantMenuListInBasket =
            restaurantFoodRepository.getAllFoodMenuListInBasket().filter { it.restaurantId != restaurantId } //장바구니에 담겨있는 식당 id가 내가 새롭게 추가한 식당id가 다를경우만 담긴 객체다.
        if (anotherRestaurantMenuListInBasket.isNotEmpty()) {                                               //새롭게 선택한 식당 id가 장바구니에 담겨있는 식당id와 다른게 있다면
            isClearNeedInBasketLiveData.value = Pair(true, { clearMenuAndInsertNewMenuInBasket(foodMenuEntity) })
        } else {  //식당 id가 다른게 없다면
            restaurantFoodRepository.insertFoodMenuInBasket(foodMenuEntity) //장바구니에 추가한다.
            menuBasketLiveData.value = foodMenuEntity
        }
    }

    private fun clearMenuAndInsertNewMenuInBasket(foodMenuEntity: RestaurantFoodEntity) = viewModelScope.launch {
        restaurantFoodRepository.clearFoodMenuListInBasket()  //장바구니를 초기화시킨다.
        restaurantFoodRepository.insertFoodMenuInBasket(foodMenuEntity) // 장바구니에 추가한다.
        menuBasketLiveData.value = foodMenuEntity
    }
}
