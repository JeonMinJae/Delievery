package mj.project.delievery.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import mj.project.delievery.R
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.databinding.FragmentHomeBinding
import mj.project.delievery.screen.base.BaseFragment
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory
import mj.project.delievery.screen.main.home.restaurant.RestaurantListFragment
import mj.project.delievery.widget.adapter.RestaurantListFragmentPagerAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val viewModel by viewModel<HomeViewModel>()

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private fun initViewPager(locationLatLng: LocationLatLngEntity) = with(binding){
        val restaurantCategories = RestaurantCategory.values() // 전체, 한식 등등

        // ::변수 는 ::을 통해서만 접근이 가능한 .isInitialized을 사용하여 lateinit 초기화를 확인할수있다. 초기화안하면 접근불가
        if(::viewPagerAdapter.isInitialized.not()){
            val restaurantListFragmentList = restaurantCategories.map{
                RestaurantListFragment.newInstance(it) // 각각의 프레그먼트와 카테고리를 map한다.
            }
            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList
            )
            viewPager.adapter = viewPagerAdapter // xml의 id값
        }
        viewPager.offscreenPageLimit = restaurantCategories.size //페이지바뀔때마다 새로만드는게 아니라 계속쓰게처리
        TabLayoutMediator(tabLayout, viewPager) { tab, position -> //텝 레이아웃에 텝들을 뿌려준다.
            tab.setText(restaurantCategories[position].categoryNameId)   //stringres 텍스트 뿌려준다. 전체/한식 등 position의 이름을 얻는다.
        }.attach()
    }
    override fun observeData() = viewModel.homeStateLiveData.observe(viewLifecycleOwner){
        when (it) {
            is HomeState.Uninitialized -> {
                getMyLocation()
            }
            is HomeState.Loading -> {
                binding.locationLoading.isVisible = true
                binding.locationTitleTextView.text = getString(R.string.loading)
            }
            is HomeState.Success -> {
                binding.locationLoading.isGone = true
                binding.locationTitleTextView.text = it.mapSearchInfo.fullAddress
                binding.tabLayout.isVisible =true
                binding.filterScrollView.isVisible = true
                binding.viewPager.isVisible = true //위치정보없을경우 숨김
                initViewPager(it.mapSearchInfo.locationLatLng)
            }
            is HomeState.Error -> {
                binding.locationLoading.isGone = true
                binding.locationTitleTextView.setText(R.string.location_not_found)
                binding.locationTitleTextView.setOnClickListener{
                    getMyLocation()
                }
                Toast.makeText(requireContext(), it.messageId, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun getMyLocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) //gps기능 체크
        if (isGpsEnable) {
            locationPermissionLauncher.launch(locationPermissions)
        }
    }

    // location permission
    // androidx requestcode가 deprecade되어서 registforactivityresult 사용하기 위해 만듬
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            //퍼미션이 들어가있는지 체크 / key, value체크
            val responsePermissions = permissions.entries.filter {
                (it.key == Manifest.permission.ACCESS_FINE_LOCATION)
                        || (it.key == Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            if (responsePermissions.filter { it.value == true }.size == locationPermissions.size) {
                setMyLocationListener() // 위치정보 불러옴
            } else {
                // 권한설정 안되어있을경우 해달라고 요청
                with(binding.locationTitleTextView) {
                    setText(R.string.please_request_location_permission) // 현재위치나타날곳에 설정해달라는문구
                    setOnClickListener {
                        getMyLocation()
                    }
                }
                Toast.makeText(requireContext(), getString(R.string.can_not_assigned_permission), Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L
        val minDistance = 100f
        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                myLocationListener
            )
            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, //네트워크 요청시
                minTime, minDistance, myLocationListener
            )
        }
    }

    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            //binding.locationTitleTextView.text = "${location.latitude}, ${location.longitude}"
            viewModel.loadReverseGeoInformation( //리버스GEO코딩 방식으로 위치정보 불러올계획 ,건물명 도로명 검색으로 위도경도x 경도위도로 건물명 도로명 알아내는방법o
                LocationLatLngEntity(
                    location.latitude,
                    location.longitude
                )
            )
            //매번불러올수 있으니 한번만 불러오게 할려고
            removeLocationListener()
        }

    }
    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }


    companion object {

        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,  //최대한 정확한 기기 위치 추정치를 제공합니다
            Manifest.permission.ACCESS_COARSE_LOCATION  // 기기 위치 추정치를 약 0.6km 이내로 제공합니다.
        )

        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

}