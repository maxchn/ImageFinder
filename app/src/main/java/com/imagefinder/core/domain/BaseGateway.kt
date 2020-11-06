package com.imagefinder.core.domain

import androidx.annotation.VisibleForTesting
import com.imagefinder.storage.remote.repository.common.error.BaseError
import com.imagefinder.storage.remote.repository.common.result.BaseResult
import com.imagefinder.storage.remote.repository.common.result.ErrorResult

abstract class BaseGateway(override val networkManager: NetworkManager) : IBaseGateway {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    suspend fun <T> executeRemote(call: suspend (() -> BaseResult<T>)): BaseResult<T> {
        networkManager.startProcessing()

        val result = call.invoke()

        networkManager.stopProcessing()

        if (result is ErrorResult<T>) {
            val message = calculateMessage(result.error)

            networkManager.errors.call(Error(message))
        }

        return result
    }

    abstract fun calculateMessage(error: BaseError): String
}
