package mj.project.delievery.widget.adapter.viewholder.food

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import mj.project.delievery.R
import mj.project.delievery.databinding.ViewholderFoodMenuBinding
import mj.project.delievery.extensions.clear
import mj.project.delievery.extensions.load
import mj.project.delievery.model.restaurant.FoodModel
import mj.project.delievery.screen.base.BaseViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.listener.AdapterListener
import mj.project.delievery.widget.adapter.listener.restaurant.FoodMenuListListener
import mj.project.delievery.widget.adapter.viewholder.ModelViewHolder

class FoodMenuViewHolder(
    private val binding: ViewholderFoodMenuBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
): ModelViewHolder<FoodModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        foodImage.clear()
    }

    override fun bindData(model: FoodModel) {
        super.bindData(model)
        with(binding) {
            foodImage.load(model.imageUrl, 24f, CenterCrop())
            foodTitleText.text = model.title
            foodDescriptionText.text = model.description
            priceText.text = resourcesProvider.getString(R.string.price, model.price)
        }
    }

    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
        if (adapterListener is FoodMenuListListener) {
            binding.root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }

}
