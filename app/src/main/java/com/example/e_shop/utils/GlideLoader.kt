package com.example.e_shop.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.e_shop.R
import java.io.IOException

class GlideLoader(val context : Context) {
    fun loadUserPicture(imageUri : Uri, imageView : ImageView){
        try {
            Glide
                .with(context)
                .load(Uri.parse(imageUri.toString()))
                .centerCrop()
                .placeholder(R.drawable.user_image_placeholder)
                .into(imageView)
        }
        catch (e : IOException){
            e.printStackTrace()
        }
    }
}