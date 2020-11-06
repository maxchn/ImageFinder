package com.imagefinder.storage.remote.di

import org.kodein.di.Kodein

object StorageRemoteModule {
    fun get() = Kodein.Module("StorageLocalModule") {
    }
}
