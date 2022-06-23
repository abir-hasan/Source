package com.example.abir.source.utils

sealed class Resource<T>(data: T? = null, message: String? = null) {
    data class Loading<T>(val data: T?, val message: String?) : Resource<T>(data, message)
    data class Success<T>(val data: T) : Resource<T>(data)
    data class Error<T>(val data: T?, val message: String) : Resource<T>(data, message)
}
