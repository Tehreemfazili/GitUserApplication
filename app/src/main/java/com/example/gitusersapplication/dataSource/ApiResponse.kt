package com.example.gitusersapplication.dataSource

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("code") @Expose val code: Int,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("message") @Expose val message: String?,
    @SerializedName("data") @Expose val data: T?,
    @SerializedName("description") @Expose val description: String?,
    @SerializedName("errors") @Expose val errors: List<String>?
)