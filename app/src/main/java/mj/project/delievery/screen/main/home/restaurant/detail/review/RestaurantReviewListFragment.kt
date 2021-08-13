package mj.project.delievery.screen.main.home.restaurant.detail.review

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.databinding.FragmentRestaurantListBinding
import mj.project.delievery.screen.base.BaseFragment
import mj.project.delievery.screen.main.home.restaurant.detail.menu.RestaurantMenuListFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment : BaseFragment<RestaurantReviewListViewModel, FragmentRestaurantListBinding>() {

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<RestaurantReviewListViewModel>()

    override fun observeData() {

    }

    companion object {
        const val RESTAURANT_ID_KEY = "restaurantId"

        fun newInstance(restaurantId: Long): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }

    }

}
