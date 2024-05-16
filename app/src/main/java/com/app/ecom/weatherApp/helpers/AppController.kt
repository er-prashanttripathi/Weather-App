
package com.app.ecom.weatherApp.helpers

import com.app.ecom.weatherApp.helpers.PreferenceManager.Companion.init
import android.app.Application
import com.app.ecom.weatherApp.data.remote.MyApi
import com.app.ecom.weatherApp.data.remote.NetworkConnectionInterceptors
import com.app.ecom.weatherApp.factories.AuthVMF
import com.app.ecom.weatherApp.repositories.SharedRepo
import com.app.ecom.weatherApp.factories.SharedVMF
import com.app.ecom.weatherApp.repositories.AuthRepo
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppController : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@AppController))
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { NetworkConnectionInterceptors(instance()) }

        bind() from singleton { SharedRepo(instance()) }
        bind() from provider { SharedVMF(instance()) }

        bind() from singleton { AuthRepo(instance()) }
        bind() from provider { AuthVMF(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        init(this)
    }

    companion object {
        var context: AppController? = null
            private set
    }
}