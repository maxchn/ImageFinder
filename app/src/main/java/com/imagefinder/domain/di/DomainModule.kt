package com.imagefinder.domain.di

import com.imagefinder.core.domain.NetworkManager
import com.imagefinder.domain.gateway.image.IImageGateway
import com.imagefinder.domain.gateway.image.ImageGateway
import com.imagefinder.domain.use_case.image.ImageInteractor
import com.imagefinder.domain.use_case.image.ImageUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object DomainModule {

    fun get() = Kodein.Module("DomainModule") {

        bind() from singleton { NetworkManager(instance()) }

        applyUseCaseModule()
        applyGatewayModule()
    }

    private fun Kodein.Builder.applyUseCaseModule() {
        bind<ImageUseCase>() with provider {
            ImageInteractor(
                instance()
            )
        }
    }

    private fun Kodein.Builder.applyGatewayModule() {

        bind<IImageGateway>() with provider {
            ImageGateway(
                errors = instance(),
                resourceReader = instance(),
                local = instance(),
                remote = instance()
            )
        }
    }
}
