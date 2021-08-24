package mj.project.delievery.data.repository.order

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import mj.project.delievery.data.entity.order.OrderEntity
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity

class DefaultOrderRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore  // 주문완료시 firestore에 저장함
) : OrderRepository {

    override suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>
    ): Result = withContext(ioDispatcher) {
        val result: Result
        val orderMenuData = hashMapOf(  //firestore에 사용 - 필드 이름
            "restaurantId" to restaurantId,
            "userId" to userId,
            "orderMenuList" to foodMenuList
        )
        result = try {
            firestore
                .collection("order") // 컬렉션 이름
                .document("상세설명")
                .set(orderMenuData)
            Result.Success<Any>()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
        return@withContext result
    }

    // 사용자가 주문한 메뉴들
    override suspend fun getAllOrderMenus(userId: String): Result = withContext(ioDispatcher) {
        return@withContext try {
            val result: QuerySnapshot = firestore  // 원하는 데이터(쿼리의 결과)를 가져올떄 snapshot으로 받아온다.
                .collection("order")
                .whereEqualTo("userId", userId) //필드 같은지 비교
                .get()
                .await()  //코루틴
            Result.Success(result.documents.map {
                OrderEntity(  //주문데이터
                    id = it.id,
                    userId = it.get("userId") as String,
                    restaurantId = it.get("restaurantId") as Long,
                    foodMenuList = (it.get("orderMenuList") as ArrayList<Map<String, Any>>).map { food ->
                        RestaurantFoodEntity(
                            id = food["id"] as String,
                            title = food["title"] as String,
                            description = food["description"] as String,
                            price = (food["price"] as Long).toInt(),
                            imageUrl = food["imageUrl"] as String,
                            restaurantId = food["restaurantId"] as Long
                        )
                    }
                )
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    //결과에 대한 반환
    sealed class Result {
        //어떤값 올지 몰라 제너릭으로 표시
        data class Success<T>(
            val data: T? = null
        ): Result()

        data class Error(
            val e: Throwable
        ): Result()
    }

}
