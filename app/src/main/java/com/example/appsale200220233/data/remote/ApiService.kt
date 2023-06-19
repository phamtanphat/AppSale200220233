package com.example.appsale200220233.data.remote

import com.example.appsale200220233.data.model.AppResponse
import com.example.appsale200220233.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by pphat on 6/14/2023.
 */
interface ApiService {

    @POST("user/sign-in")
    suspend fun signIn(
        @Body hashMap: Map<String, String>
    ): AppResponse<User>
}
