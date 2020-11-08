package com.imagefinder.features.featuresMain

import com.imagefinder.features.featuresMain.common.ImagesAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

object MainModule {

    fun get() = Kodein.Module("MainModule") {
        bind<MainContract.ViewModel>() with provider {
            MainViewModel(
                resourceReader = instance(),
                imageUseCase = instance(),
                networkManager = instance()
            )
        }
        bind<ImagesAdapter>() with provider { ImagesAdapter() }
    }
}
