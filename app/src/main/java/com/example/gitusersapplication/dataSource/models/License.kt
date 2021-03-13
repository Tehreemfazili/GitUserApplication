package com.example.gitusersapplication.dataSource.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("key")
    @Expose
    val key: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("spdx_id")
    @Expose
    val spdxId: String,
    @SerializedName("url")
    @Expose
    val url: Object,
    @SerializedName("node_id")
    @Expose
    val nodeId: String
)