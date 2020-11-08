package com.imagefinder.features.featuresMain

import androidx.lifecycle.LiveData
import com.imagefinder.core.domain.NetworkManager
import com.imagefinder.core.domain.livedata.SingleLiveManager
import com.imagefinder.core.presentation.common.BaseViewModel
import com.imagefinder.domain.entity.ImageItem

interface MainContract {

    interface ViewModel : BaseViewModel {
        val images: LiveData<MutableList<ImageItem>>
        val message: SingleLiveManager<String>
        val newImageItem: SingleLiveManager<ImageItem>
        val isLoading: SingleLiveManager<Boolean>
        val networkManager: NetworkManager

        fun searchImage(queryValue: String)
        fun clearSearchHistory()
    }
}
