package com.imagefinder.di

import android.content.Context
import com.imagefinder.App
import com.imagefinder.core.presentation.AndroidResourceReader
import com.imagefinder.core.presentation.ResourceReader
import com.imagefinder.domain.di.DomainModule
import com.imagefinder.storage.local.di.StorageLocalModule
import com.imagefinder.storage.remote.di.StorageRemoteModule
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object AppModule {

    fun module(application: App) = Kodein.Module("AppModule") {
        import(androidModule(application))

        import(StorageRemoteModule.get())
        import(StorageLocalModule.get())
        import(DomainModule.get())

        bind<Context>() with provider { application.applicationContext }
        bind<String>(tag = "appPackageName") with singleton { application.packageName }
        bind<ResourceReader>() with singleton { AndroidResourceReader(instance()) }
    }
}
