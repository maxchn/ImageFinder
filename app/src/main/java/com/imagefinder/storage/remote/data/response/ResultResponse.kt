package com.imagefinder.storage.remote.data.response

import com.google.gson.annotations.SerializedName

class ResultResponse(
    @SerializedName("value")
    val values: List<ImageItemResponse>?= null
)
