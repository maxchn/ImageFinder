package com.imagefinder.storage.local.repository.image

import com.imagefinder.storage.local.data.ImageItemDto
import io.reactivex.Observable

interface ImageRepository {
    fun getAll(): Observable<List<ImageItemDto>>
    fun upsert(newItem: ImageItemDto)
    fun removeAll()
}
