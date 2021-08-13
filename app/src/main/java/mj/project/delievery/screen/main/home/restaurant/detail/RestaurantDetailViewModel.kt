package mj.project.delievery.screen.main.home.restaurant.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.delievery.data.entity.restaurant.RestaurantEntity
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
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity //detailstate.entity = restaurantentity
        )
        restaurantDetailStateLiveData.value = RestaurantDetailState.Loading
        val foods = restaurantFoodRepository.getFoods(restaurantId = restaurantEntity.restaurantInfoId)
        val isLiked = userRepository.getUserLikedRestaurant(restaurantEntity.restaurantTitle) != null
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity,
            restaurantFoodList = foods,
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
                // userLiskedRestaurant가 있다 -> 토글버튼눌렀으므로 없애줘야한다.
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


}
