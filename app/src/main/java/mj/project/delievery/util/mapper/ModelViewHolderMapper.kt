package mj.project.delievery.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import mj.project.delievery.databinding.*
import mj.project.delievery.model.CellType
import mj.project.delievery.model.Model
import mj.project.delievery.screen.base.BaseViewModel
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.viewholder.ModelViewHolder
import mj.project.delievery.widget.adapter.viewholder.food.FoodMenuViewHolder
import mj.project.delievery.widget.adapter.viewholder.order.OrderMenuViewHolder
import mj.project.delievery.widget.adapter.viewholder.order.OrderViewHolder
import mj.project.delievery.widget.adapter.viewholder.restaurant.LikeRestaurantViewHolder
import mj.project.delievery.widget.adapter.viewholder.restaurant.RestaurantViewHolder
import mj.project.delievery.widget.adapter.viewholder.review.RestaurantReviewViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M: Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when (type) {
            CellType.RESTAURANT_CELL -> RestaurantViewHolder(
                    ViewholderRestaurantBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider  //리소스프로바이더를 넘겨주면은 viewholder에서 string값을 가져올수있게끔한다.
            )
            CellType.FOOD_CELL -> FoodMenuViewHolder(
                    ViewholderFoodMenuBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
            )
            CellType.REVIEW_CELL -> RestaurantReviewViewHolder(
                    ViewholderRestaurantReviewBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
            )
            CellType.LIKE_RESTAURANT_CELL -> LikeRestaurantViewHolder(
                    ViewholderLikeRestaurantBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
            )
            CellType.ORDER_FOOD_CELL -> OrderMenuViewHolder(
                    ViewholderOrderMenuBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
            )
            CellType.ORDER_CELL -> OrderViewHolder(
                    ViewholderOrderBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
            )
        }
        return viewHolder as ModelViewHolder<M>
    }

}
