package com.example.appsale200220233.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by pphat on 6/14/2023.
 */
data class User(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("token")
    var token: String = "",
)
