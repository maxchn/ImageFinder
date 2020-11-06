package com.imagefinder.storage.remote.repository.common.result

class SuccessResult<T>(
    val data: T
) : BaseResult<T>()
