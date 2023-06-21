package com.example.appsale200220233.common.utils

import androidx.annotation.WorkerThread
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by pphat on 6/21/2023.
 */
object ThrowableUtils {
    private val gson by lazy {
        GsonBuilder().create()
    }

    class ErrorTypeToken : TypeToken<ErrorResponse>()

    @WorkerThread
    suspend fun parseExceptionHttp(exception: Exception): ErrorResponse? {
        return when (exception) {
            is HttpException -> {
                return withContext(Dispatchers.Default) {
                    gson.fromJson(
                        exception.response()?.errorBody()?.string(),
                        ErrorTypeToken().type
                    )
                }
            }
            else -> null
        }
    }
}

data class ErrorResponse(
    @SerializedName("message")
    var message: String = ""
)
