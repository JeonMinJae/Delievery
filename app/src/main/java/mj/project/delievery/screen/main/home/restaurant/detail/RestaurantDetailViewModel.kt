package mj.project.delievery.screen.main.home.restaurant.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.delievery.data.entity.restaurant.RestaurantEntity
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.data.repository.restaurant.food.RestaurantFoodRepository
import mj.project.delievery.data.repository.user.UserRepository
import mj.project.delievery.screen.base.BaseViewModel

class RestaurantDetailViewModel(
    private val restaurantEntity: RestaurantEntity,
    private val restaurantFoodRepository: RestaurantFoodRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val restaurantDetailStateLiveData = MutableLiveData<RestaurantDetailState>(RestaurantDetailState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantDetailStateLiveData.value = RestaurantDetailState.Loading
        val foods = restaurantFoodRepository.getFoods(restaurantId = restaurantEntity.restaurantInfoId)
        val foodMenuListInBasket = restaurantFoodRepository.getAllFoodMenuListInBasket()
        val isLiked = userRepository.getUserLikedRestaurant(restaurantEntity.restaurantTitle) != null
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity,
            restaurantFoodList = foods,
            foodMenuListInBasket = foodMenuListInBasket,
            isLiked = isLiked
        )
    }

    fun getRestaurantTelNumber(): String? {
        return when(val data = restaurantDetailStateLiveData.value){
            is RestaurantDetailState.Success -> {
                data.restaurantEntity.restaurantTelNumber
            }
            else -> null
        }
    }

    fun toggleLikedRestaurant() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                // userLikedRestaurant가 있다 -> 토글버튼눌렀으므로 없애줘야한다.
                userRepository.getUserLikedRestaurant(restaurantEntity.restaurantTitle)?.let {
                    userRepository.deleteUserLikedRestaurant(it.restaurantTitle)
                    restaurantDetailStateLiveData.value = data.copy( // copy()는 객체의 복사본을 만들어 리턴합니다.copy()의 인자로 생성자에 정의된 프로퍼티를 넘길 수 있으며, 그 프로퍼티의 값만 변경되고 나머지 값은 동일한 객체가 생성됩니다.
                        isLiked = false
                    )
                } ?: kotlin.run { // userLiskedRestaurant가 없다. -> 토글버튼눌렀으므로 만들어 줘야한다.
                    userRepository.insertUserLikedRestaurant(restaurantEntity)
                    restaurantDetailStateLiveData.value = data.copy(
                        isLiked = true
                    )
                }
            }
            else -> Unit
        }
    }

    fun getRestaurantInfo(): RestaurantEntity? {
        return when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                data.restaurantEntity
            }
            else -> null
        }
    }

    fun notifyFoodMenuListInBasket(foodMenu: RestaurantFoodEntity) = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = data.foodMenuListInBasket?.toMutableList()?.apply {
                        add(foodMenu)  // 장바구니 리스트에 선택한 foodmenu를 추가한다.
                    }
                )
            }
            else -> Unit
        }
    }

    // <Pair<Boolean, () -> Unit>>()  true면 초기화 false는 초기화x
    fun notifyClearNeedAlertInBasket(isClearNeed: Boolean, afterAction: () -> Unit) = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    isClearNeedInBasketAndAction = Pair(isClearNeed, afterAction)
                )
            }
            else -> Unit
        }
    }

    // 장바구니 초기화됨을 알려주는 함수
    fun notifyClearBasket() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = listOf(),
                    isClearNeedInBasketAndAction = Pair(false, {})
                )
            }
            else -> Unit
        }
    }
}
