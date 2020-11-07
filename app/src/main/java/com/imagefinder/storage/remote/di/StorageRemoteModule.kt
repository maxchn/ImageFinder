package com.imagefinder.storage.remote.di

import com.imagefinder.BuildConfig
import com.imagefinder.core.remote.RetrofitConfig
import com.imagefinder.core.remote.RetrofitModule
import com.imagefinder.storage.remote.api.ImageApi
import com.imagefinder.storage.remote.repository.image.ImageRepository
import com.imagefinder.storage.remote.repository.image.ImageRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import retrofit2.Retrofit

object StorageRemoteModule {
    fun get() = Kodein.Module("StorageLocalModule") {
        // Retrofit
        configureRetrofit()
        import(RetrofitModule.get())

        // Api's
        bind<ImageApi>() with provider { instance<Retrofit>().create(ImageApi::class.java) }

        // Repositories
        bind<ImageRepository>() with provider { ImageRepositoryImpl(instance()) }
    }

    private fun configureRetrofit() {
        RetrofitConfig.apply {
            baseUrl = BuildConfig.BASE_URL

            enableLogging()
        }
    }
}
