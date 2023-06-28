package com.example.appsale200220233.data.remote

import android.content.Context
import com.example.appsale200220233.common.AppConstant
import com.example.appsale200220233.data.local.SharePreferenceApp
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by pphat on 6/14/2023.
 */
object RetrofitClient {
    private var instance: Retrofit? = null
    fun getApiService(context: Context): ApiService {
        if (instance == null) {
            instance = createRetrofit(context)
        }
        return instance!!.create(ApiService::class.java)
    }

    private fun createRetrofit(context: Context): Retrofit {

        val logRequest = HttpLoggingInterceptor()
        logRequest.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logRequest)
            .addInterceptor(Interceptor { chain ->
                val token = SharePreferenceApp.getStringValue(context, AppConstant.TOKEN_KEY)
                val request = chain.request().newBuilder()
                if (!token.isNullOrEmpty()) {
                    request.addHeader("Authorization", "Bearer $token")
                }
                return@Interceptor chain.proceed(request.build())
            })
            .build()

        val gson = GsonBuilder().create()

        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
