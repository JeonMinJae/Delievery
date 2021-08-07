package mj.project.delievery.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.tabs.TabLayoutMediator
import mj.project.delievery.R
import mj.project.delievery.databinding.FragmentHomeBinding
import mj.project.delievery.screen.base.BaseFragment
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory
import mj.project.delievery.screen.main.home.restaurant.RestaurantListFragment
import mj.project.delievery.widget.adapter.RestaurantListFragmentPagerAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val viewModel by viewModel<HomeViewModel>()

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun initViews(){
        super.initViews()
        initViewPager()

    }
    private fun initViewPager()= with(binding){
        val restaurantCategories = RestaurantCategory.values() // 전체, 한식 등등

        if(::viewPagerAdapter.isInitialized.not()){
            val restaurantListFragmentList = restaurantCategories.map{
                RestaurantListFragment.newInstance(it)
            }
            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList
            )
            viewPager.adapter = viewPagerAdapter
        }
        viewPager.offscreenPageLimit = restaurantCategories.size //페이지바뀔때마다 새로만드는게 아니라 계속쓰게처리
        TabLayoutMediator(tabLayout, viewPager) { tab, position -> //텝 레이아웃에 텝들을 뿌려준다.
            tab.setText(restaurantCategories[position].categoryNameId)   //stringres 텍스트 뿌려준다. 전체/한식 등 position의 이름을 얻는다.
        }.attach()
    }
    override fun observeData() {}

    companion object {

        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

}