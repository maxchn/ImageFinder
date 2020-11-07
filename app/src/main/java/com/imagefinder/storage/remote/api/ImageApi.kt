package com.imagefinder.storage.remote.api

import com.imagefinder.BuildConfig
import com.imagefinder.storage.remote.data.response.ResultResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageApi {
    @Headers(
        "x-rapidapi-host: ${BuildConfig.API_HOST}",
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "useQueryString: true"
    )
    @GET("images/search")
    fun searchImageAsync(@Query("q") queryValue: String): Deferred<Response<ResultResponse>>
}
