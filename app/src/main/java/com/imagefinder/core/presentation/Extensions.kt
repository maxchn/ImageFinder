package com.imagefinder.core.presentation

import com.imagefinder.storage.remote.repository.common.result.BaseResult
import com.imagefinder.storage.remote.repository.common.result.SuccessResult
import io.reactivex.ObservableEmitter
import timber.log.Timber
import java.io.Closeable

fun <T, M> BaseResult<T>.mapDataIfSuccess(mapper: ((item: T) -> M)): M? {
    if (this !is SuccessResult<T>) {
        return null
    }

    return mapper.invoke(this.data)
}

inline fun <T : Closeable?, R> T.useRealm(block: (T) -> R): R {
    val result: R = try {
        block(this)
    } catch (e: Throwable) {
        Timber.e(e)

        this.closeConn()

        throw e
    }

    this.closeConn()

    return result
}

inline fun <T : Closeable?, R> T.useRealmWithObservable(
    observableEmitter: ObservableEmitter<R>,
    block: (T) -> R
) {
    try {
        val result = block(this)
        observableEmitter.onNext(result)
        observableEmitter.onComplete()
    } catch (e: Throwable) {
        Timber.wtf(e)

        observableEmitter.onError(e)

        this.closeConn()
    }

    this.closeConn()
}

fun <T : Closeable?> T.closeConn() {
    when {
        this == null -> {
        }
        else ->
            try {
                close()
            } catch (closeException: Throwable) {
                // ignored here
            }
    }
}
