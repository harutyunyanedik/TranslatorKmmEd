package com.example.translatorkmmed.core.domain.util

sealed class Resource<T>(data: T?, throwable: Throwable? = null) {

    data class Success<T>(val data: T?) : Resource<T>(data)

    data class Error<T>(val throwable: Throwable?) : Resource<T>(null, throwable)
}