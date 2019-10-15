package com.rxrequester.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import com.rxrequester.app.di.appModule
import com.rxrequester.app.ui.restaurants.restaurantsModule
import com.sha.kamel.navigator.NavigatorOptions
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * Created by Sha on 13/04/17.
 */

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
            context = applicationContext

            startKoin {
                androidContext(this@MyApp)
            }
            loadKoinModules(listOf(appModule, restaurantsModule))

            NavigatorOptions.instance().frameLayoutId = R.id.mainFrame
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @JvmStatic
        fun string(@StringRes res: Int): String {
            return context.getString(res)
        }

    }
}
