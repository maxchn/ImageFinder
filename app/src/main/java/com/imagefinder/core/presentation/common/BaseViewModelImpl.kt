package com.imagefinder.core.presentation.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModelImpl : ViewModel(), BaseViewModel {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        clearSubscriptions()

        super.onCleared()
    }

    fun clearSubscriptions() = compositeDisposable.clear()

    protected fun Disposable.autoDispose(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}
