package com.example.e_shop.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class ESEditText(context:Context, attrs:AttributeSet): AppCompatEditText(context,attrs) {
    init {
        appFont()

    }
    private fun appFont() {
        val typeface : Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}