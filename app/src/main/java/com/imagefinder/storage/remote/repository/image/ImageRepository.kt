package com.imagefinder.storage.remote.repository.image

import com.imagefinder.storage.remote.data.response.ResultResponse
import com.imagefinder.storage.remote.repository.common.result.BaseResult

interface ImageRepository {
    suspend fun searchImage(queryValue: String): BaseResult<ResultResponse>
}
