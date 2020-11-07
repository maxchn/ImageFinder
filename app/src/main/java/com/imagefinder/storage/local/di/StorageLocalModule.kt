package com.imagefinder.storage.local.di

import com.imagefinder.storage.local.repository.common.RealmLocalStorage
import com.imagefinder.storage.local.repository.image.ImageRepository
import com.imagefinder.storage.local.repository.image.ImageRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object StorageLocalModule {
    fun get() = Kodein.Module("StorageLocalModule") {
        bind<RealmLocalStorage>() with singleton { RealmLocalStorage() }

        bind<ImageRepository>() with provider { ImageRepositoryImpl(instance()) }
    }
}
