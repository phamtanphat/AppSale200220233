package com.example.appsale200220233.common.utils

import android.icu.text.DecimalFormat

/**
 * Created by pphat on 6/14/2023.
 */
object StringUtils {
    fun formatCurrency(number: Int): String {
        return DecimalFormat("#,###").format(number)
    }
}
