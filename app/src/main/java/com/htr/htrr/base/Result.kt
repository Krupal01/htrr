package com.htr.htrr.base

import retrofit2.Response

sealed class Result<out T> {
    data class Success<T>(val data: T? = null, val code: Int? = 200) : Result<T>()
    data class Error(
        val exception: Throwable,
        val code: Int? = null,
        val message: String? = null
    ) : Result<Nothing>()
}

fun <T> Response<T>.getResult(): Result<T> {
    return if (isSuccessful) {
        body()?.let {
            Result.Success(it, code())
        } ?: Result.Success(code = code())
    } else {
        Result.Error(
            exception = Exception("API call failed"),
            code = this.code(),
            message = this.message()
        )
    }
}