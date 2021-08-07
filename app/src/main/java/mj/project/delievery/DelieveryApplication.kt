package mj.project.delievery

import android.app.Application
import android.content.Context
import mj.project.delievery.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DelieveryApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            androidLogger(Level.ERROR)   //에러 레벨로 안드로이드 로그를 남긴다.
            androidContext(this@DelieveryApplication) // 이거 안쓰면 에러뜸
            modules(appModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }

    companion object {
        var appContext: Context? = null
            private set //내부에서만 사용할거라서
    }

}