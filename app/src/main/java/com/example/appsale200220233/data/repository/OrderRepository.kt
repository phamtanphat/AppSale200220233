package com.example.appsale200220233.data.repository

import android.content.Context
import com.example.appsale200220233.data.model.AppResponse
import com.example.appsale200220233.data.model.Order
import com.example.appsale200220233.data.remote.RetrofitClient

class OrderRepository(var context: Context) {
    private var apiService = RetrofitClient.getApiService(context)

    suspend fun addCart(idProduct: String): AppResponse<Order> {
        val map = HashMap<String, String>()
        map["id_product"] = idProduct
        return apiService.addCart(map)
    }

    suspend fun fetchCart(): AppResponse<Order> {
        return apiService.fetchCart()
    }
}