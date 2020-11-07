package com.imagefinder.storage.remote.repository.image

import com.imagefinder.storage.remote.api.ImageApi
import com.imagefinder.storage.remote.data.response.ResultResponse
import com.imagefinder.storage.remote.repository.common.BaseRepository
import com.imagefinder.storage.remote.repository.common.result.BaseResult

class ImageRepositoryImpl(
    private val api: ImageApi
) : BaseRepository(), ImageRepository {

    override suspend fun searchImage(queryValue: String): BaseResult<ResultResponse> {
        return execute { api.searchImageAsync(queryValue) }.transformIsSuccess { it }
    }
}
