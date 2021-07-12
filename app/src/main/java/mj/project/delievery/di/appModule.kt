package mj.project.delievery.di

import kotlinx.coroutines.Dispatchers
import mj.project.delievery.util.provider.DefaultResourcesProvider
import mj.project.delievery.util.provider.ResourcesProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

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
