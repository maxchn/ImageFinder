package com.imagefinder.storage.remote.data.response

import com.google.gson.annotations.SerializedName

class ImageItemResponse(
    @SerializedName("contentUrl")
    val contentUrl: String? = null
)
