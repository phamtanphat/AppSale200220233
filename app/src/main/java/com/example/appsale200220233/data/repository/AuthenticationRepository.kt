package com.example.appsale200220233.data.repository

import com.example.appsale200220233.data.model.AppResponse
import com.example.appsale200220233.data.model.User
import com.example.appsale200220233.data.remote.ApiService
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.remote.RetrofitClient

/**
 * Created by pphat on 6/19/2023.
 */
class AuthenticationRepository {
    private var apiService: ApiService = RetrofitClient.getApiService()

    suspend fun requestLogin(email: String, password: String): AppResponse<User> {
        val mapString = HashMap<String, String>()
        mapString["email"] = email
        mapString["password"] = password
        return apiService.signIn(mapString)
    }
}
