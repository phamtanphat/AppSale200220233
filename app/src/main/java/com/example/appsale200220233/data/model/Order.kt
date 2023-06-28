package com.example.appsale200220233.data.model

import com.google.gson.annotations.SerializedName

data class Order (
    @SerializedName("_id")
    var idOrder: String = "",
    @SerializedName("products")
    var listProduct: List<Product> = mutableListOf(),
    @SerializedName("id_user")
    var idUser: String = "",
    @SerializedName("price")
    var price: Long = 0,
)