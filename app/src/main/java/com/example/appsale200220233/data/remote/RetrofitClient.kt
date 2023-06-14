package com.example.appsale200220233.data.remote

import com.example.appsale200220233.common.AppConstant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by pphat on 6/14/2023.
 */
object RetrofitClient {
    private var instance: Retrofit = createRetrofit()
    private var apiService = instance.create(ApiService::class.java)

    fun getApiService(): ApiService = apiService

    private fun createRetrofit(): Retrofit {

        val logRequest = HttpLoggingInterceptor()
        logRequest.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logRequest)
            .build()

        val gson = GsonBuilder().create()

        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
