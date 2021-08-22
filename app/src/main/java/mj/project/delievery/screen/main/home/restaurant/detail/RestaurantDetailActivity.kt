package mj.project.delievery.screen.main.home.restaurant.detail

import android.app.AlertDialog
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import mj.project.delievery.R
import mj.project.delievery.data.entity.restaurant.RestaurantEntity
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.databinding.ActivityRestaurantDetailBinding
import mj.project.delievery.extensions.fromDpToPx
import mj.project.delievery.extensions.load
import mj.project.delievery.screen.base.BaseActivity
import mj.project.delievery.screen.main.MainTabMenu
import mj.project.delievery.screen.main.home.restaurant.RestaurantListFragment
import mj.project.delievery.screen.main.home.restaurant.detail.menu.RestaurantMenuListFragment
import mj.project.delievery.screen.main.home.restaurant.detail.review.RestaurantReviewListFragment
import mj.project.delievery.screen.order.OrderMenuListActivity
import mj.project.delievery.util.event.MenuChangeEventBus
import mj.project.delievery.widget.adapter.RestaurantDetailListFragmentPagerAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.abs

class RestaurantDetailActivity : BaseActivity<RestaurantDetailViewModel, ActivityRestaurantDetailBinding>() {

    override val viewModel by viewModel<RestaurantDetailViewModel>{
        parametersOf(
            //Parcelable은 intent로 데이터 전달 시 객체 자체를 전달 할 수 있도록 해준다
            intent.getParcelableExtra<RestaurantEntity>(RestaurantListFragment.RESTAURANT_KEY)
        )
    }

    override fun getViewBinding(): ActivityRestaurantDetailBinding = ActivityRestaurantDetailBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantDetailListFragmentPagerAdapter

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    override fun initViews() {
        initAppBar()
    }

    override fun observeData() = viewModel.restaurantDetailStateLiveData.observe(this) {
        when (it) {
            is RestaurantDetailState.Loading -> {
                handleLoading()
            }
            is RestaurantDetailState.Success -> {
                handleSuccess(it)
            }
            else -> Unit
        }
    }

    private fun initAppBar() = with(binding) {
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset -> //스크롤시 앱바의 크기상쇄하는 리스너다.
            val topPadding = 300f.fromDpToPx().toFloat()
            val realAlphaScrollHeight = appBarLayout.measuredHeight - appBarLayout.totalScrollRange // 실제 다 펴진크기 - 스크롤 가능한 범위
            val abstractOffset = abs(verticalOffset) //절대값으로 움직인값, 기준으로 얼마나 떨어져있는지

            val realAlphaVerticalOffset: Float = if (abstractOffset - topPadding < 0) 0f  //기준으로 움직인게 300f보다 적을경우
            else abstractOffset - topPadding

            if (abstractOffset < topPadding) {
                restaurantTitleTextView.alpha = 0f  //투명도는 0 = 선명
                return@OnOffsetChangedListener
            }
            val percentage = realAlphaVerticalOffset / realAlphaScrollHeight
            restaurantTitleTextView.alpha = 1 - (if (1 - percentage * 2 < 0) 0f else 1 - percentage * 2) // 완전투명 1 - 움직인 퍼센트만큼
        })
        toolbar.setNavigationOnClickListener {
            finish()
        }
        callButton.setOnClickListener {
            viewModel.getRestaurantTelNumber()?.let { telNumber ->
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telNumber")) // 전화앱이 실행된다.
                startActivity(intent)
            }
        }
        likeButton.setOnClickListener {
            viewModel.toggleLikedRestaurant()
        }
        shareButton.setOnClickListener {
            viewModel.getRestaurantInfo()?.let { restaurantInfo ->
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = MIMETYPE_TEXT_PLAIN //ClipDescription - 평범한 텍스트
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "맛있는 음식점 : ${restaurantInfo.restaurantTitle}" +
                                "\n평점 : ${restaurantInfo.grade}" +
                                "\n연락처 : ${restaurantInfo.restaurantTelNumber}"
                    )
                    Intent.createChooser(this, "친구에게 공유하기")
                }
                startActivity(intent)
            }
        }
    }

    private fun handleLoading() = with(binding) {
        progressBar.isVisible = true
    }

    private fun handleSuccess(state: RestaurantDetailState.Success) = with(binding) {
        progressBar.isGone = true

        val restaurantEntity = state.restaurantEntity

        // callButton.visibility = if (restaurantEntity.restaurantTelNumber == null) callButton.Gone else callButton.visible 이것을 Core ktx를 사용한것이다.
        callButton.isGone = restaurantEntity.restaurantTelNumber == null  // telnumber가 없으면 전화버튼 제거

        restaurantTitleTextView.text = restaurantEntity.restaurantTitle
        restaurantImage.load(restaurantEntity.restaurantImageUrl)
        restaurantMainTitleTextView.text = restaurantEntity.restaurantTitle
        ratingBar.rating = restaurantEntity.grade
        ratingTextView.text = restaurantEntity.grade.toString()
        deliveryTimeText.text =
            getString(R.string.delivery_expected_time, restaurantEntity.deliveryTimeRange.first, restaurantEntity.deliveryTimeRange.second)
        deliveryTipText.text =
            getString(R.string.delivery_tip_range, restaurantEntity.deliveryTipRange.first, restaurantEntity.deliveryTipRange.second)

        //찜버튼
        likeText.setCompoundDrawablesWithIntrinsicBounds( //버튼에 왼쪽, 위, 오른쪽, 아래 에 이미지를 넣을수 있다.
            ContextCompat.getDrawable(this@RestaurantDetailActivity, if (state.isLiked == true) {
                R.drawable.ic_heart_enable
            } else {
                R.drawable.ic_heart_disable
            }),
            null, null, null
        )

        if (::viewPagerAdapter.isInitialized.not()) {
            initViewPager(state.restaurantEntity.restaurantInfoId, state.restaurantEntity.restaurantTitle , state.restaurantFoodList)
        }

        notifyBasketCount(state.foodMenuListInBasket)

        val (isClearNeed, afterAction) = state.isClearNeedInBasketAndAction

        if (isClearNeed) {
            alertClearNeedInBasket(afterAction)
        }
    }

    private fun initViewPager(restaurantInfoId: Long, restaurantTitle: String, restaurantFoodList: List<RestaurantFoodEntity>?) {
        viewPagerAdapter = RestaurantDetailListFragmentPagerAdapter(
            this,
            listOf(
                RestaurantMenuListFragment.newInstance(
                    restaurantInfoId,
                    ArrayList(restaurantFoodList ?: listOf())
                ),

                RestaurantReviewListFragment.newInstance(
                    restaurantTitle
                )
            )
        )
        binding.menuAndReviewViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.menuAndReviewTabLayout, binding.menuAndReviewViewPager) { tab, position ->
            tab.setText(RestaurantDetailCategory.values()[position].categoryNameId)
        }.attach()

    }

    // 장바구니안에 데이터 몇개들어있는지 알려주는 함수
    private fun notifyBasketCount(foodMenuListInBasket: List<RestaurantFoodEntity>?) = with(binding) {
        basketCountTextView.text = if (foodMenuListInBasket.isNullOrEmpty()) {  //널이거나 없다면
            "0"
        } else {
            getString(R.string.basket_count, foodMenuListInBasket.size) //있다면 데이터 갯수 표시
        }
        basketButton.setOnClickListener {
            if (firebaseAuth.currentUser == null) {
                alertLoginNeed {
                    lifecycleScope.launch {
                        menuChangeEventBus.changeMenu(MainTabMenu.MY)
                        finish()
                    }
                }
            } else {
                if (foodMenuListInBasket.isNullOrEmpty()) {
                    Toast.makeText(this@RestaurantDetailActivity, "장바구니에 주문할 메뉴를 추가해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                startActivity(
                    OrderMenuListActivity.newIntent(this@RestaurantDetailActivity)
                )
            }
        }
    }

    private fun alertLoginNeed(afterAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("로그인이 필요합니다.")
            .setMessage("주문하려면 로그인이 필요합니다. My탭으로 이동하시겠습니까?")
            .setPositiveButton("이동") { dialog, _ ->
                afterAction()
                dialog.dismiss() // <->cancel(메시지전달후, dismiss호출) , dismiss()- UI 스레드만이 다이얼로그를 종료시킨다
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    // Alert dialog - 장바구니 초기화 될수도 있을때
    private fun alertClearNeedInBasket(afterAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("장바구니에는 같은 가게의 메뉴만 담을 수 있습니다.")
            .setMessage("선택하신 메뉴를 장바구니에 담을 경우 이전에 담은 메뉴가 삭제됩니다.")
            .setPositiveButton("담기") { dialog, _ ->  //텍스트와 리스너가 들어가는데 리스너가 사용안되서 익명으로 _를 사용했다.
                viewModel.notifyClearBasket()
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    companion object {
        fun newIntent(context: Context, restaurantEntity: RestaurantEntity) = Intent(context, RestaurantDetailActivity::class.java).apply {
            putExtra(RestaurantListFragment.RESTAURANT_KEY, restaurantEntity)
        }
    }


}
