package com.example.appsale200220233.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by pphat on 6/26/2023.
 */
data class Product (
    @SerializedName("_id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("price")
    var price: Long = 0,
    @SerializedName("img")
    var image: String = "",
    @SerializedName("quantity")
    var quantity: String = "",
    @SerializedName("gallery")
    var gallery: List<String> = mutableListOf()
)
