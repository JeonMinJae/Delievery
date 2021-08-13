package mj.project.delievery.screen.main.home.restaurant.detail.menu

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.databinding.FragmentRestaurantListBinding
import mj.project.delievery.model.restaurant.FoodModel
import mj.project.delievery.screen.base.BaseFragment
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.ModelRecyclerAdapter
import mj.project.delievery.widget.adapter.listener.AdapterListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantMenuListFragment : BaseFragment<RestaurantMenuListViewModel, FragmentRestaurantListBinding>() {

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    private val restaurantId by lazy { arguments?.getLong(RESTAURANT_ID_KEY, -1) } //key,value

    private val restaurantFoodList by lazy { arguments?.getParcelableArrayList<RestaurantFoodEntity>(FOOD_LIST_KEY)!! } //null값이 절대 오면 안되서 !! 사용

    // viewmodel에 parameter값을 전달해주는 역할
    override val viewModel by viewModel<RestaurantMenuListViewModel>{parametersOf(restaurantId, restaurantFoodList) }

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<FoodModel, RestaurantMenuListViewModel>(
            listOf(), viewModel, resourcesProvider, adapterListener = object : AdapterListener{}
        )
    }

    override fun initViews(){
        binding.recyclerVIew.adapter = adapter
    }

    override fun observeData() = viewModel.restaurantMenuListLiveData.observe(this) {
        adapter.submitList(it)
    }

    companion object {
        const val RESTAURANT_ID_KEY = "restaurantId"
        const val FOOD_LIST_KEY = "foodList"

        fun newInstance(restaurantId: Long, foodList: ArrayList<RestaurantFoodEntity>): RestaurantMenuListFragment {

            // arguments = Bundle().apply{putInt("key",value)} 를 core ktx사용해서 arguments = bundleOf("key" to value)로 바꾼거다.
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId,
                FOOD_LIST_KEY to foodList
            )

            return RestaurantMenuListFragment().apply {
                arguments = bundle
            }
        }

    }

}
