package mj.project.delievery.widget.adapter.viewholder.review

import androidx.core.view.isGone
import androidx.core.view.isVisible
import mj.project.delievery.databinding.ViewholderRestaurantReviewBinding
import mj.project.delievery.extensions.clear
import mj.project.delievery.extensions.load
import mj.project.delievery.model.restaurant.RestaurantReviewModel
import mj.project.delievery.screen.base.BaseViewModel
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.listener.AdapterListener
import mj.project.delievery.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding: ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<RestaurantReviewModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        reviewThumbnailImage.clear()
        reviewThumbnailImage.isGone = true
    }

    override fun bindData(model: RestaurantReviewModel) {
        super.bindData(model)
        with(binding) {
            if (model.thumbnailImageUri != null) {
                reviewThumbnailImage.isVisible = true
                reviewThumbnailImage.load(model.thumbnailImageUri.toString(), 24f)
            } else {
                reviewThumbnailImage.isGone = true
            }

            reviewTitleText.text = model.title
            reviewText.text = model.description
            ratingBar.rating = model.grade.toFloat()
        }
    }

    override fun bindViews(model: RestaurantReviewModel, adapterListener: AdapterListener) = Unit

}
