package com.example.e_shop.utils


import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class ESTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context,attrs) {
    init {
        appFont()
    }

    private fun appFont() {
        val typeface : Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Regular.ttf")
        setTypeface(typeface)
    }

}