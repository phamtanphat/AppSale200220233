package com.example.appsale200220233.data.repository

import com.example.appsale200220233.data.model.AppResponse
import com.example.appsale200220233.data.model.Product
import com.example.appsale200220233.data.remote.RetrofitClient

/**
 * Created by pphat on 6/26/2023.
 */
class ProductRepository {
    private var apiService = RetrofitClient.getApiService()

    suspend fun getListProduct(): AppResponse<List<Product>> = apiService.getListProducts()
}
