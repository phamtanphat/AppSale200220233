package com.example.appsale200220233.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by pphat on 6/19/2023.
 */
data class AppResponse<T>(
    @SerializedName("data")
    var data: T? = null,
    @SerializedName("message")
    var message: String = ""
)
