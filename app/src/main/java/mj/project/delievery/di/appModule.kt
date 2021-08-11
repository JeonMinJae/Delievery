package mj.project.delievery.di

import kotlinx.coroutines.Dispatchers
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity
import mj.project.delievery.data.entity.restaurant.RestaurantEntity
import mj.project.delievery.data.repository.map.DefaultMapRepository
import mj.project.delievery.data.repository.map.MapRepository
import mj.project.delievery.data.repository.restaurant.DefaultRestaurantRepository
import mj.project.delievery.data.repository.restaurant.RestaurantRepository
import mj.project.delievery.data.repository.user.DefaultUserRepository
import mj.project.delievery.data.repository.user.UserRepository
import mj.project.delievery.screen.main.home.HomeViewModel
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory
import mj.project.delievery.screen.main.home.restaurant.RestaurantListViewModel
import mj.project.delievery.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import mj.project.delievery.screen.main.my.MyViewModel
import mj.project.delievery.screen.mylocation.MyLocationViewModel
import mj.project.delievery.util.provider.DefaultResourcesProvider
import mj.project.delievery.util.provider.ResourcesProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //ViewModel
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory : RestaurantCategory, locationLatLng: LocationLatLngEntity) -> RestaurantListViewModel(restaurantCategory,locationLatLng,get()) }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity, get(), get()) }
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity) }

    // Repository
    single<RestaurantRepository> { DefaultRestaurantRepository(get(),get(),get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get()) }

    // ProvideAPI
    single { provideMapRetrofit(get(),get()) }
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    //service
    single { provideMapApiService(get()) }

    // ResourcesProvider
    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    //room
    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }

    // 코루틴
    single { Dispatchers.IO }
    single { Dispatchers.Main }


}
