package com.example.appsale200220233.common.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by pphat on 6/23/2023.
 */
object ToastUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
