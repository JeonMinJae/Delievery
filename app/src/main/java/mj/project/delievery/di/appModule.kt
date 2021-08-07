package mj.project.delievery.di

import kotlinx.coroutines.Dispatchers
import mj.project.delievery.data.repository.DefaultRestaurantRepository
import mj.project.delievery.data.repository.RestaurantRepository
import mj.project.delievery.screen.main.MainViewModel
import mj.project.delievery.screen.main.home.HomeViewModel
import mj.project.delievery.screen.main.home.restaurant.RestaurantCategory
import mj.project.delievery.screen.main.home.restaurant.RestaurantListViewModel
import mj.project.delievery.screen.main.my.MyViewModel
import mj.project.delievery.util.provider.DefaultResourcesProvider
import mj.project.delievery.util.provider.ResourcesProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //ViewModel
    viewModel { HomeViewModel() }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory : RestaurantCategory) -> RestaurantListViewModel(restaurantCategory,get()) }


    // Repository
    single<RestaurantRepository> { DefaultRestaurantRepository(get(),get()) }

    // ProvideAPI
    single { provideRetrofit(get(),get()) }
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    // ResourcesProvider
    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    // 코루틴
    single { Dispatchers.IO }
    single { Dispatchers.Main }


}
