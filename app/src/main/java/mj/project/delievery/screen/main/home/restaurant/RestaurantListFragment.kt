package mj.project.delievery.screen.main.home.restaurant

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import mj.project.delievery.databinding.FragmentRestaurantListBinding
import mj.project.delievery.model.restaurant.RestaurantModel
import mj.project.delievery.screen.base.BaseFragment
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.ModelRecyclerAdapter
import mj.project.delievery.widget.adapter.listener.restaurant.RestaurantListListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class RestaurantListFragment : BaseFragment<RestaurantListViewModel, FragmentRestaurantListBinding>() {

    private val restaurantCategory by lazy { arguments?.getSerializable(RESTAURANT_CATEGORY_KEY) as RestaurantCategory }

    override val viewModel by viewModel<RestaurantListViewModel> { parametersOf(restaurantCategory)}

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantModel, RestaurantListViewModel>(listOf(), viewModel, resourcesProvider, adapterListener = object :
            RestaurantListListener {
            override fun onClickItem(model: RestaurantModel) {
                Toast.makeText(requireContext(), "$model", Toast.LENGTH_SHORT).show()
                /*startActivity(
                    RestaurantDetailActivity.newIntent(requireContext(), model.toEntity())
                )*/
            }
        })
    }

    override fun initViews() = with(binding) {
        recyclerVIew.adapter = adapter  // fragment_RESTAURANT_list의 recyclerview의 id값이다.
    }

    override fun observeData() = viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
        Log.e("restaurantList", it.toString())
        adapter.submitList(it)  // adapter의 mutablelist인지 list인지 잘보자
    }

    companion object {
        const val RESTAURANT_CATEGORY_KEY = "restaurantCategory"

        fun newInstance(restaurantCategory: RestaurantCategory): RestaurantListFragment {
            return RestaurantListFragment().apply {
                arguments = bundleOf(
                    RESTAURANT_CATEGORY_KEY to restaurantCategory
                )
            }
        }

    }
}