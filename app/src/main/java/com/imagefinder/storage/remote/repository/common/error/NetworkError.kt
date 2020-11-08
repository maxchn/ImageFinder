package com.imagefinder.storage.remote.repository.common.error

class NetworkError(
    val code: Int = -1,
    val message: String = ""
) : BaseError()
