package com.imagefinder.domain.gateway.image

import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.storage.local.data.ImageItemDto
import io.reactivex.Observable

interface IImageGateway {
    suspend fun searchImage(queryValue: String): List<ImageItem>?
    fun saveImage(imageItem: ImageItem)
    fun getAllImages() : Observable<List<ImageItemDto>>
    fun removeAllImages()
}
