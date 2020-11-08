package com.imagefinder.core.presentation

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.imagefinder.storage.remote.repository.common.result.BaseResult
import com.imagefinder.storage.remote.repository.common.result.SuccessResult
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

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.findViewById<View>(android.R.id.content).windowToken, 0)
}
