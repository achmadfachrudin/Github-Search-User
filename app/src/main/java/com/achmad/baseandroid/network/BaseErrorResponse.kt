package com.achmad.baseandroid.network

/**
 * A customized error response.
 *
 * @param code A network response code.
 * @param message A network error message.
 */
data class BaseErrorResponse(
    val code: Int,
    val message: String?
)
