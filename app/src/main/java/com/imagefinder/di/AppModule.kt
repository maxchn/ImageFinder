package com.imagefinder.di

import com.imagefinder.App
import com.imagefinder.domain.di.DomainModule
import com.imagefinder.storage.local.di.StorageLocalModule
import com.imagefinder.storage.remote.di.StorageRemoteModule
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

object AppModule {

    fun module(application: App) = Kodein.Module("AppModule") {
        import(androidModule(application))

        import(StorageRemoteModule.get())
        import(StorageLocalModule.get())
        import(DomainModule.get())

        bind<String>(tag = "appPackageName") with singleton { application.packageName }
    }
}
