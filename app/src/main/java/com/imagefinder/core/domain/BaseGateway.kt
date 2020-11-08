package com.imagefinder.core.domain

import androidx.annotation.VisibleForTesting
import com.imagefinder.R
import com.imagefinder.core.presentation.ResourceReader
import com.imagefinder.storage.remote.repository.common.error.BaseError
import com.imagefinder.storage.remote.repository.common.error.NetworkError
import com.imagefinder.storage.remote.repository.common.result.BaseResult
import com.imagefinder.storage.remote.repository.common.result.ErrorResult

abstract class BaseGateway(
    override val networkManager: NetworkManager,
    private val resourceReader: ResourceReader
) : IBaseGateway {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    suspend fun <T> executeRemote(call: suspend (() -> BaseResult<T>)): BaseResult<T> {
        return if (networkManager.isOnline()) {
            networkManager.startProcessing()

            val result = call.invoke()

            networkManager.stopProcessing()

            if (result is ErrorResult<T>) {
                val message = calculateMessage(result.error)

                networkManager.errors.call(Error(message))
            }

            result
        } else {
            networkManager.errors.call(Error(resourceReader.getString(R.string.error_no_internet_connection)))

            ErrorResult(
                NetworkError(message = resourceReader.getString(R.string.error_no_internet_connection))
            )
        }
    }

    abstract fun calculateMessage(error: BaseError): String
}
