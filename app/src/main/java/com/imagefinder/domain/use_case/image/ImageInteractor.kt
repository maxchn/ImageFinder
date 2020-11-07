package com.imagefinder.domain.use_case.image

import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.domain.gateway.image.IImageGateway
import io.reactivex.Observable

class ImageInteractor(
    private val gateway: IImageGateway
) : ImageUseCase {

    override suspend fun searchImage(queryValue: String): List<ImageItem>? {
        return gateway.searchImage(queryValue)
    }

    override fun saveImage(imageItem: ImageItem) {
        gateway.saveImage(imageItem)
    }

    override fun getAllImages(): Observable<List<ImageItem>> {
        return gateway.getAllImages()
    }

    override fun removeAllImages() {
        gateway.removeAllImages()
    }
}
