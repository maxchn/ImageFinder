package com.imagefinder.features.featuresMain

import androidx.lifecycle.MutableLiveData
import com.imagefinder.R
import com.imagefinder.core.domain.NetworkManager
import com.imagefinder.core.domain.livedata.SingleLiveManager
import com.imagefinder.core.presentation.ResourceReader
import com.imagefinder.core.presentation.common.BaseViewModelImpl
import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.domain.use_case.image.ImageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val resourceReader: ResourceReader,
    private val imageUseCase: ImageUseCase,
    override val networkManager: NetworkManager
) : BaseViewModelImpl(), MainContract.ViewModel {

    override val images = MutableLiveData<MutableList<ImageItem>>()

    override val message = SingleLiveManager<String>()

    override val isLoading = SingleLiveManager<Boolean>()

    init {
        imageUseCase.getAllImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    images.postValue(it.reversed().toMutableList())
                },
                {
                    Timber.e(it)
                }
            ).autoDispose()
    }

    override fun searchImage(queryValue: String) {
        isLoading.call(true)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = imageUseCase.searchImage(queryValue)

                when {
                    result != null && result.isEmpty() -> showImageNotFoundMessage()
                    result != null -> calculateAndSaveNewImageItem(result)
                }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                isLoading.call(false)
            }
        }
    }

    override fun clearSearchHistory() {
        isLoading.call(true)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                imageUseCase.removeAllImages()
                images.postValue(mutableListOf())

                message.call(
                    resourceReader.getString(R.string.clear_search_history_success)
                )
            } catch (e: Exception) {
                Timber.e(e)

                message.call(
                    resourceReader.getString(R.string.error_unknown)
                )
            } finally {
                isLoading.call(false)
            }
        }
    }

    private fun showImageNotFoundMessage() {
        message.call(
            resourceReader.getString(R.string.search_image_is_not_found)
        )
    }

    private fun calculateAndSaveNewImageItem(result: List<ImageItem>) {
        val randomItem = result.random()

        imageUseCase.saveImage(randomItem)

        images.value?.add(0, randomItem)
        images.postValue(images.value)
    }
}
