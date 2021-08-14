package mj.project.delievery.screen.main.home.restaurant.detail.menu

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.databinding.FragmentRestaurantListBinding
import mj.project.delievery.model.restaurant.FoodModel
import mj.project.delievery.screen.base.BaseFragment
import mj.project.delievery.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.ModelRecyclerAdapter
import mj.project.delievery.widget.adapter.listener.AdapterListener
import mj.project.delievery.widget.adapter.listener.restaurant.FoodMenuListListener
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

    // by sharedViewModel()을 통하여 activity의 ViewModel을 받아올 수 있다.
    //그러면 activity와 sharedViewModel로 주입받은 fragment 모두 같은 ViewModel을 사용하기 때문에 데이터가 변하면 livedata를 통해 모든 뷰가 이벤트를 받을 수 있다.
    private val restaurantDetailViewModel by sharedViewModel<RestaurantDetailViewModel>()

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<FoodModel, RestaurantMenuListViewModel>(
            listOf(), viewModel, resourcesProvider, adapterListener = object :
                FoodMenuListListener {
                override fun onClickItem(model: FoodModel) {
                    viewModel.insertMenuInBasket(model)  //메뉴를 장바구니에 담는다.
                }
            })
    }

    override fun initViews(){
        binding.recyclerVIew.adapter = adapter
    }

    override fun observeData() {
        viewModel.restaurantMenuListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)  //어뎁터를 하나씩 보여준다.
        }
        viewModel.menuBasketLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "장바구니에 담겼습니다. 메뉴 : ${it.title}", Toast.LENGTH_SHORT).show()
            restaurantDetailViewModel.notifyFoodMenuListInBasket(it)
        }
        viewModel.isClearNeedInBasketLiveData.observe(viewLifecycleOwner) { (isClearNeed, afterAction) ->  //<Pair<Boolean, () -> Unit>>
            if (isClearNeed) {  //true면 데이터 초기화 할것이고 false면 아무런 조치를 하지않는다.
                restaurantDetailViewModel.notifyClearNeedAlertInBasket(isClearNeed, afterAction)
            }
        }
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
