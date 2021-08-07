package mj.project.delievery.widget.adapter.viewholder.restaurant

import mj.project.delievery.R
import mj.project.delievery.databinding.ViewholderRestaurantBinding
import mj.project.delievery.extensions.clear
import mj.project.delievery.extensions.load
import mj.project.delievery.model.restaurant.RestaurantModel
import mj.project.delievery.screen.base.BaseViewModel
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.listener.AdapterListener
import mj.project.delievery.widget.adapter.listener.restaurant.RestaurantListListener
import mj.project.delievery.widget.adapter.viewholder.ModelViewHolder


class RestaurantViewHolder(
    private val binding: ViewholderRestaurantBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
): ModelViewHolder<RestaurantModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        restaurantImage.clear()
    }

    override fun bindData(model: RestaurantModel) {
        super.bindData(model)
        with(binding) {
            restaurantImage.load(model.restaurantImageUrl, 24f)
            restaurantTitleText.text = model.restaurantTitle
            gradeText.text = resourcesProvider.getString(R.string.grade_format, model.grade)
            reviewCountText.text = resourcesProvider.getString(R.string.review_count, model.reviewCount)
            val (minTime, maxTime) = model.deliveryTimeRange
            deliveryTimeText.text = resourcesProvider.getString(R.string.delivery_time, minTime, maxTime)

            val (minTip, maxTip) = model.deliveryTipRange
            deliveryTipText.text = resourcesProvider.getString(R.string.delivery_tip, minTip, maxTip)
        }
    }

    override fun bindViews(model: RestaurantModel, adapterListener: AdapterListener) = with(binding) {
        if (adapterListener is RestaurantListListener) {
            //xml의 view를 클릭시 여기서 root를 viewholder 그 자체이다.
            root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }

}
