package com.imagefinder.storage.remote.repository.common.error

class NetworkError(
    val code: Int,
    val message: String
) : BaseError()
