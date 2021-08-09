package mj.project.delievery.screen.main.home.restaurant

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
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

    private val locationLatLng by lazy { arguments?.getParcelable<LocationLatLngEntity>(LOCATION_KEY) }

    override val viewModel by viewModel<RestaurantListViewModel> { parametersOf(restaurantCategory, locationLatLng)} // lazy해서 얻은 category를 viewmoel에 주입받는다.

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

    //mutablelivedata.observe
    // viewLifecycleOwner 바뀔때마다 확인
    override fun observeData() = viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
        Log.e("restaurantList", it.toString())
        adapter.submitList(it)  // modelRecycleradapter의 mutablelist인지 list인지 잘보자
    }

    companion object {
        const val RESTAURANT_CATEGORY_KEY = "restaurantCategory"
        const val LOCATION_KEY = "location"

        //새로운 fragment를 생성할때 argument에 bundle을 넘겨줌으로서 데이터 넘겨줄수있고 여기서는 key값과 value를 pair형태로 넘겨준다.
        fun newInstance(
            restaurantCategory: RestaurantCategory,
            locationLatLng: LocationLatLngEntity
        ): RestaurantListFragment {
            return RestaurantListFragment().apply {
                arguments = bundleOf(
                    RESTAURANT_CATEGORY_KEY to restaurantCategory, //to를 사용하여 pair객체 생성
                    LOCATION_KEY to locationLatLng
                )
            }
        }

    }
}