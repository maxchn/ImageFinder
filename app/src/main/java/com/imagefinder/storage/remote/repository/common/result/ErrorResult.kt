package com.imagefinder.storage.remote.repository.common.result

import com.imagefinder.storage.remote.repository.common.error.BaseError

class ErrorResult<T>(
    val error: BaseError
) : BaseResult<T>()
