package com.imagefinder.domain.use_case.image

import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.storage.local.data.ImageItemDto
import io.reactivex.Observable

interface ImageUseCase {
    suspend fun searchImage(queryValue: String) : List<ImageItem>?
    fun saveImage(imageItem: ImageItem)
    fun getAllImages() : Observable<List<ImageItemDto>>
    fun removeAllImages()
}
