package com.example.gitusersapplication.remote

import com.example.gitusersapplication.dataSource.ApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun parseErrorBody(errorBody: String?): ApiResponse<*>? {
    errorBody ?: return null
    return try {
        Gson().fromJson(errorBody, object : TypeToken<ApiResponse<*>>() {}.type)
    } catch (e: Exception) {
        null
    }
}

fun isSessionExpired(code: Int?): Boolean {
    return ErrorCodes.SESSION_EXPIRED == code || ErrorCodes.CUSTOMER_DISABLED == code
}

class ErrorCodes {

    companion object {
        const val SESSION_EXPIRED = 777
        const val CUSTOMER_DISABLED = 702
        const val TOKEN_EXPIRED = 841
    }

}