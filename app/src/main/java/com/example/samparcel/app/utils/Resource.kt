package com.samparcel.app.utils

sealed class Resource<T> {
    class Idle<T> : Resource<T>()
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(
        val message: String,
        val data: T? = null
    ) : Resource<T>()
}