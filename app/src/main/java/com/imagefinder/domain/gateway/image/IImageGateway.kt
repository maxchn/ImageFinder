package com.imagefinder.domain.gateway.image

import com.imagefinder.domain.entity.ImageItem
import io.reactivex.Observable

interface IImageGateway {
    suspend fun searchImage(queryValue: String): List<ImageItem>?
    fun saveImage(imageItem: ImageItem)
    fun getAllImages(): Observable<List<ImageItem>>
    fun removeAllImages()
}
