package com.example.e_shop.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class ESRadioButton(context : Context, attributeSet: AttributeSet) : AppCompatRadioButton(context,attributeSet) {
    init {
        appFont()
    }
    private fun appFont() {
        val typeface : Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}