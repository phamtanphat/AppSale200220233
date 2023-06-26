package com.example.appsale200220233.data.remote

import com.example.appsale200220233.data.model.AppResponse
import com.example.appsale200220233.data.model.Product
import com.example.appsale200220233.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by pphat on 6/14/2023.
 */
interface ApiService {

    @POST("user/sign-in")
    suspend fun signIn(
        @Body hashMap: Map<String, String>
    ): AppResponse<User>

    @POST("user/sign-up")
    suspend fun signUp(
        @Body hashMap: Map<String, String>
    ): AppResponse<User>

    @GET("product")
    suspend fun getListProducts(): AppResponse<List<Product>>
}
