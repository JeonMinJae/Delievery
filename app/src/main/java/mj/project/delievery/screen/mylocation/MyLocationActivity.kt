package mj.project.delievery.screen.mylocation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import mj.project.delievery.R
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity
import mj.project.delievery.databinding.ActivityMyLocationBinding
import mj.project.delievery.screen.base.BaseActivity
import mj.project.delievery.screen.main.home.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MyLocationActivity : BaseActivity<MyLocationViewModel, ActivityMyLocationBinding>(), OnMapReadyCallback {

    // () 와 {} 를 헷갈리지말자
    override val viewModel by viewModel<MyLocationViewModel>{
        parametersOf(intent.getParcelableExtra<MapSearchInfoEntity>(HomeViewModel.MY_LOCATION_KEY))
    }

    private lateinit var map: GoogleMap

    private var isMapInitialized: Boolean = false
    private var isChangingLocation: Boolean = false

    override fun getViewBinding() = ActivityMyLocationBinding.inflate(layoutInflater)

    companion object {
        const val CAMERA_ZOOM_LEVEL = 17f

        fun newIntent(context: Context, mapSearchInfoEntity: MapSearchInfoEntity) =
            Intent(context, MyLocationActivity::class.java).apply {
                putExtra(HomeViewModel.MY_LOCATION_KEY, mapSearchInfoEntity)
            }

    }

    override fun initViews() = with(binding) {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        confirmButton.setOnClickListener{

        }
        setupGoogleMap()
    }

    override fun observeData() {
        viewModel.myLocationStateLiveData.observe(this) {
            when (it) {
                is MyLocationState.Loading -> {
                    handleLoadingState()
                }
                is MyLocationState.Success -> {
                    if (::map.isInitialized) {
                        handleSuccessState(it)
                    }
                }
                is MyLocationState.Confirm -> {

                }
                is MyLocationState.Error -> {
                    Toast.makeText(this, it.messageId, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
    }
    private fun handleLoadingState() = with(binding) {
        locationLoading.isVisible = true
        locationTitleTextView.text = getString(R.string.loading)
    }

    private fun handleSuccessState(state: MyLocationState.Success) = with(binding) {
        val mapSearchInfo = state.mapSearchInfoEntity
        locationLoading.isGone = true
        locationTitleTextView.text = mapSearchInfo.fullAddress
        if (isMapInitialized.not()) {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        mapSearchInfo.locationLatLng.latitude,
                        mapSearchInfo.locationLatLng.longitude
                    ), CAMERA_ZOOM_LEVEL //카메라 확대비율
                )
            )

            //지도가 멈춰있는지 판단하는 작업
            map.setOnCameraIdleListener {
                if (isChangingLocation.not()) {
                    isChangingLocation = true
                    Handler(Looper.getMainLooper()).postDelayed({ //메인스레드에서 작업
                        val cameraLatLng = map.cameraPosition.target
                        viewModel.changeLocationInfo(
                            LocationLatLngEntity(
                                cameraLatLng.latitude,
                                cameraLatLng.longitude
                            )
                        )
                        isChangingLocation = false //호출이 되면 false로 바꿔줌
                    }, 1000) //1초동안 동작없으면 api불러오게한다.
                }
            }
            isMapInitialized = true
        }
    }

    //구글맵 객체가져와서 관리해줘야함
    private fun setupGoogleMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment //xml의 android:name
        mapFragment.getMapAsync(this)
    }

    // 맵이 있는경우
    override fun onMapReady(map: GoogleMap?) {
        this.map = map ?: return
        viewModel.fetchData()
    }

}
