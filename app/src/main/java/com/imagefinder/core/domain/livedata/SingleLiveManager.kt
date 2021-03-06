package com.imagefinder.core.domain.livedata

import androidx.lifecycle.LifecycleOwner

class SingleLiveManager<T : Any>(val defValue: T? = null) {
    private val event = SingleLiveEvent<T>()

    fun call(data: T) {
        event.postValue(data)
    }

    fun call() {
        checkNotNull(defValue) { "You must set default value for this call" }

        event.postValue(defValue)
    }

    fun observe(owner: LifecycleOwner, observer: ((item: T?) -> Unit)) {
        event.observe(owner, {
            observer.invoke(it)
        })
    }
}
