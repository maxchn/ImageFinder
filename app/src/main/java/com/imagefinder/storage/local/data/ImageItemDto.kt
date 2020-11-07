package com.imagefinder.storage.local.data

import io.realm.RealmObject

open class ImageItemDto(
    var id: Long = 0,
    var queryValue: String? = null,
    var url: String? = null
) : RealmObject() {

    fun init(rawItem: ImageItemDto) {
        id = rawItem.id
        queryValue = rawItem.queryValue
        url = rawItem.url
    }
}
