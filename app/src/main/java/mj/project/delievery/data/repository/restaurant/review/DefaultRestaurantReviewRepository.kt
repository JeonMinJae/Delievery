package mj.project.delievery.data.repository.restaurant.review

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mj.project.delievery.data.entity.restaurant.RestaurantReviewEntity

class DefaultRestaurantReviewRepository(
    private val ioDispatcher: CoroutineDispatcher,
): RestaurantReviewRepository {

    @Suppress("UNCHECKED_CAST")
    override suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity> = withContext(ioDispatcher) {
        return@withContext (0..10).map {
            RestaurantReviewEntity(
                id = 0,
                title = "제목 $it",
                description = "내용 $it",
                grade = (1 until 5).random() //이미지는 널러블해서 생략
            )
        }
    }

}
