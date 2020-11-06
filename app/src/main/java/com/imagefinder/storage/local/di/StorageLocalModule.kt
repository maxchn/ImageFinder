package com.imagefinder.storage.local.di

import com.imagefinder.storage.local.repository.common.RealmLocalStorage
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

object StorageLocalModule {
    fun get() = Kodein.Module("StorageLocalModule") {
        bind<RealmLocalStorage>() with singleton { RealmLocalStorage() }
    }
}
