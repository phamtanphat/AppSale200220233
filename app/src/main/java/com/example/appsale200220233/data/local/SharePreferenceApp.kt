package com.example.appsale200220233.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Created by pphat on 6/14/2023.
 */
object SharePreferenceApp {
    private const val preferenceName = "app_reference"

    private fun create(context: Context): SharedPreferences {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
    }

    fun setStringValue(context: Context, key: String, value: String) = create(context).edit {
        putString(key, value)
    }

    fun getStringValue(context: Context, key: String): String? {
        return create(context).getString(key, "")
    }
}
