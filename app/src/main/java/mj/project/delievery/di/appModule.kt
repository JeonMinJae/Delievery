package mj.project.delievery.di

import kotlinx.coroutines.Dispatchers
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity
import mj.project.delievery.data.entity.restaurant.RestaurantEntity
import mj.project.delievery.data.entity.restaurant.RestaurantFoodEntity
import mj.project.delievery.data.repository.map.DefaultMapRepository
import mj.project.delievery.data.repository.map.MapRepository
import mj.project.delievery.data.repository.restaurant.DefaultRestaurantRepository
import mj.project.delievery.data.repository.restaurant.RestaurantRepository
import mj.project.delievery.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import mj.project.delievery.data.repository.restaurant.food.RestaurantFoodRepository
import mj.project.delievery.data.repository.user.DefaultUserRepository
import mj.project.delievery.data.repository.user.UserRepository
import mj.project.delievery.screen.main.home.HomeViewModel
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory
import mj.project.delievery.screen.main.home.restaurant.RestaurantListViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.review.RestaurantReviewListViewModel
import mj.project.delievery.screen.main.my.MyViewModel
import mj.project.delievery.screen.mylocation.MyLocationViewModel
import mj.project.delievery.util.provider.DefaultResourcesProvider
import mj.project.delievery.util.provider.ResourcesProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    //ViewModel
    //appmodule에 적혀있지 않은건 람다안에 '()->'형식으로 주입받게 해주고
    //appmodule에 적혀있는 것은 get() 으로 주입받게 한다.
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory : RestaurantCategory, locationLatLng: LocationLatLngEntity) -> RestaurantListViewModel(restaurantCategory,locationLatLng,get()) }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity, get(), get()) }
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity, get(),get()) }
    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>) -> RestaurantMenuListViewModel(restaurantId, restaurantFoodList) }
    viewModel { RestaurantReviewListViewModel() }


    // Repository
    single<RestaurantRepository> { DefaultRestaurantRepository(get(),get(),get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get(),get()) }
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(), get()) }

    // ProvideAPI
    single(named("map")) { provideMapRetrofit(get(),get()) }  //koin - named (qualifier), 같은 Return 값을 가질 때 구별하기 위해 작성
    single(named("food")) { provideFoodRetrofit(get(), get()) } //동일 타입의 Instance가 여러 개 필요하다면 qualifier를 지정하여 만들 수 있다

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

    // 코루틴
    single { Dispatchers.IO }
    single { Dispatchers.Main }


}
