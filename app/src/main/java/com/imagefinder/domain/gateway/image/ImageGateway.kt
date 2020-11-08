package com.imagefinder.domain.gateway.image

import com.imagefinder.R
import com.imagefinder.core.domain.BaseGateway
import com.imagefinder.core.domain.NetworkManager
import com.imagefinder.core.presentation.ResourceReader
import com.imagefinder.core.presentation.mapDataIfSuccess
import com.imagefinder.domain.common.mapper.local.toImageItemDto
import com.imagefinder.domain.common.mapper.remote.toImages
import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.storage.remote.repository.common.error.BaseError
import com.imagefinder.storage.remote.repository.common.error.NetworkError
import io.reactivex.Observable
import com.imagefinder.storage.local.repository.image.ImageRepository as Local
import com.imagefinder.storage.remote.repository.image.ImageRepository as Remote

class ImageGateway(
    errors: NetworkManager,
    private val resourceReader: ResourceReader,
    private val local: Local,
    private val remote: Remote
) : BaseGateway(errors, resourceReader), IImageGateway {

    override fun calculateMessage(error: BaseError): String {
        if (error is NetworkError) {
            return when (error.code) {
                500 -> resourceReader.getString(R.string.error_server)
                else -> resourceReader.getString(R.string.error_unknown)
            }
        }

        return ""
    }

    override suspend fun searchImage(queryValue: String): List<ImageItem>? {
        return executeRemote { remote.searchImage(queryValue) }.mapDataIfSuccess {
            it.values?.toImages(
                queryValue
            )
        }
    }

    override fun saveImage(imageItem: ImageItem) {
        local.upsert(imageItem.toImageItemDto())
    }

    override fun getAllImages(): Observable<List<ImageItem>> {
        return local.getAll()
    }

    override fun removeAllImages() {
        local.removeAll()
    }
}
