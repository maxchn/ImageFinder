package com.imagefinder.core.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import com.imagefinder.core.domain.livedata.SingleLiveManager
import com.imagefinder.core.presentation.activity.BaseActivity

class NetworkManager(
    private val context: Context
) {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isOnline(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return when {
            capabilities == null -> false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    val errors = SingleLiveManager<Error>()

    val processing = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    fun startProcessing() {
        processing.postValue(true)
    }

    fun stopProcessing() {
        processing.postValue(false)
    }

    fun applyDefaultObserver(activity: BaseActivity) {
        errors.observe(activity) {
            activity.showToast(it?.message ?: return@observe)
        }
    }
}
