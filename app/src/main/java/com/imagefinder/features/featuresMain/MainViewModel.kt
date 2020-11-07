package com.imagefinder.features.featuresMain

import androidx.lifecycle.MutableLiveData
import com.imagefinder.R
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
    private val imageUseCase: ImageUseCase
) : BaseViewModelImpl(), MainContract.ViewModel {

    override val images = MutableLiveData<List<ImageItem>>()

    override val message = SingleLiveManager<String>()

    override val newImageItem = SingleLiveManager<ImageItem>()

    init {
        imageUseCase.getAllImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    images.postValue(it)
                },
                {
                    Timber.e(it)
                }
            )
    }

    override fun searchImage(queryValue: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = imageUseCase.searchImage(queryValue)

            when {
                result.isNullOrEmpty() -> message.call(
                    resourceReader.getString(R.string.search_image_is_not_found)
                )
                else -> {
                    val randomItem = result.random()
                    newImageItem.call(randomItem)

                    imageUseCase.saveImage(randomItem)
                }
            }
        }
    }
}
