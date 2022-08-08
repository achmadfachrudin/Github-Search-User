package com.achmad.common

sealed class ApiResult<out T>(
    val status: ApiStatus,
    val data: T?,
    val error: String?
) {

    data class Success<out R>(val _data: R) : ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        error = null
    )

    data class Error(val _error: String) : ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        error = _error
    )

    object Loading : ApiResult<Nothing>(
        status = ApiStatus.LOADING,
        data = null,
        error = null
    )

    enum class ApiStatus {
        SUCCESS,
        ERROR,
        LOADING
    }
}
