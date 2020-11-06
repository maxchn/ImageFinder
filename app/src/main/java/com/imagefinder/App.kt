package com.imagefinder

import android.app.Application
import com.imagefinder.di.AppModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import timber.log.Timber

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(AppModule.module(this@App))
    }

    override val kodeinTrigger = KodeinTrigger()

    override fun onCreate() {
        super.onCreate()

        kodeinTrigger.trigger()

        Realm.init(applicationContext)

        val config: RealmConfiguration = RealmConfiguration.Builder()
            .name(BuildConfig.DB_NAME)
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(config)

        Timber.plant(Timber.DebugTree())
    }
}
