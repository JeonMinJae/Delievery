package mj.project.delievery.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity
import mj.project.delievery.data.entity.restaurant.RestaurantEntity
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.data.preference.AppPreferenceManager
import mj.project.delievery.data.repository.map.DefaultMapRepository
import mj.project.delievery.data.repository.map.MapRepository
import mj.project.delievery.data.repository.order.DefaultOrderRepository
import mj.project.delievery.data.repository.order.OrderRepository
import mj.project.delievery.data.repository.restaurant.DefaultRestaurantRepository
import mj.project.delievery.data.repository.restaurant.RestaurantRepository
import mj.project.delievery.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import mj.project.delievery.data.repository.restaurant.food.RestaurantFoodRepository
import mj.project.delievery.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import mj.project.delievery.data.repository.restaurant.review.RestaurantReviewRepository
import mj.project.delievery.data.repository.user.DefaultUserRepository
import mj.project.delievery.data.repository.user.UserRepository
import mj.project.delievery.screen.main.home.HomeViewModel
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory
import mj.project.delievery.screen.main.home.restaurant.RestaurantListViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.review.RestaurantReviewListViewModel
import mj.project.delievery.screen.main.like.RestaurantLikeListViewModel
import mj.project.delievery.screen.main.my.MyViewModel
import mj.project.delievery.screen.mylocation.MyLocationViewModel
import mj.project.delievery.screen.order.OrderMenuListViewModel
import mj.project.delievery.util.event.MenuChangeEventBus
import mj.project.delievery.util.provider.DefaultResourcesProvider
import mj.project.delievery.util.provider.ResourcesProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    //ViewModel
    //appmodule??? ???????????? ????????? ???????????? '()->'???????????? ???????????? ?????????
    //appmodule??? ???????????? ?????? get() ?????? ???????????? ??????.
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MyViewModel(get(),get(),get()) }
    viewModel { (restaurantCategory : RestaurantCategory, locationLatLng: LocationLatLngEntity) -> RestaurantListViewModel(restaurantCategory,locationLatLng,get()) }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity, get(), get()) }
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity, get(),get()) }
    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>) -> RestaurantMenuListViewModel(restaurantId, restaurantFoodList, get()) }
    viewModel { (restaurantTitle: String) -> RestaurantReviewListViewModel(restaurantTitle,get()) }
    viewModel { RestaurantLikeListViewModel(get()) }
    viewModel { OrderMenuListViewModel(get(),get()) }

    // Repository
    single<RestaurantRepository> { DefaultRestaurantRepository(get(),get(),get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get(),get()) }
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(), get(), get()) }
    single<RestaurantReviewRepository> {DefaultRestaurantReviewRepository(get())}
    single<OrderRepository>{ DefaultOrderRepository(get(),get()) }

    // ProvideAPI
    single(named("map")) { provideMapRetrofit(get(),get()) }  //koin - named (qualifier), ?????? Return ?????? ?????? ??? ???????????? ?????? ??????
    single(named("food")) { provideFoodRetrofit(get(), get()) } //?????? ????????? Instance??? ?????? ??? ??????????????? qualifier??? ???????????? ?????? ??? ??????

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    //service
    single { provideMapApiService(get(qualifier = named("map"))) }
    single { provideFoodApiService(get(qualifier = named("food"))) }

    // ResourcesProvider
    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    //room
    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }
    single { provideRestaurantDao(get()) }
    single { provideFoodMenuBasketDao(get())}

    //preferenceManager
    single { AppPreferenceManager(androidApplication())}

    // ?????????
    single { Dispatchers.IO }
    single { Dispatchers.Main }

    // util
    single { MenuChangeEventBus() }

    //firestore
    single {Firebase.firestore}
}
