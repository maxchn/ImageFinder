package com.imagefinder.domain.common.mapper.remote

import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.storage.remote.data.response.ImageItemResponse

fun ImageItemResponse.toImage(queryValue: String): ImageItem {
    return ImageItem(
        queryValue = queryValue,
        url = this@toImage.contentUrl.orEmpty()
    )
}

fun List<ImageItemResponse>.toImages(queryValue: String): List<ImageItem> {
    return this.map { it.toImage(queryValue) }
}
