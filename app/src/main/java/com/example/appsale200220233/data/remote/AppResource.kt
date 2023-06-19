package com.example.appsale200220233.data.remote

import com.example.appsale200220233.data.model.User

/**
 * Created by pphat on 6/14/2023.
 */
sealed class AppResource<out T: Any> {
    data class SUCCESS<out T: Any>(val data: T?): AppResource<T>()
    data class ERROR(val message: Throwable): AppResource<Nothing>()
    object LOADING: AppResource<Nothing>()
}
