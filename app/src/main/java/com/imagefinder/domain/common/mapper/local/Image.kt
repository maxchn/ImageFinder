package com.imagefinder.domain.common.mapper.local

import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.storage.local.data.ImageItemDto

fun ImageItem.toImageItemDto(): ImageItemDto {
    return ImageItemDto(
        id = this@toImageItemDto.id,
        queryValue = this@toImageItemDto.queryValue,
        url = this@toImageItemDto.url
    )
}

fun ImageItemDto.toImageItem(): ImageItem {
    return ImageItem(
        id = this@toImageItem.id,
        queryValue = this@toImageItem.queryValue,
        url = this@toImageItem.url
    )
}