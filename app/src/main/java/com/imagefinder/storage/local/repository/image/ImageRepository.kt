package com.imagefinder.storage.local.repository.image

import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.storage.local.data.ImageItemDto
import io.reactivex.Observable

interface ImageRepository {
    fun getAll(): Observable<List<ImageItem>>
    fun upsert(newItem: ImageItemDto)
    fun removeAll()
}
