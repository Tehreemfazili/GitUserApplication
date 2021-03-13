package com.example.gitusersapplication.remote

sealed class NetworkState<out T> {
    class Loading<out T> : NetworkState<T>()
    data class Success<out T>(val data: T?, val message: String? = null, val code: Int? = null, val description : String ?= null) : NetworkState<T>()
    data class Failure<out T>(val throwable: Throwable) : NetworkState<T>()
    data class Error<out T>(
        val message: String,
        val errorCode: String = "",
        val isSessionExpired: Boolean = false
    ) : NetworkState<T>()
}