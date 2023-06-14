package com.example.appsale200220233.common.utils

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.example.appsale200220233.R


/**
 * Created by pphat on 6/14/2023.
 */
object SpannedUtils {

    fun setClickColorLink(
        text: String?,
        context: Context,
        onListenClick: () -> Unit
    ): SpannableStringBuilder {
        val builder = SpannableStringBuilder()
        builder.append(text)
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                onListenClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = context.resources.getColor(R.color.primary)
            }
        }, 0, builder.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        return builder
    }
}
