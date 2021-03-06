package com.example.e_shop.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

object Constants {
    const val USERS :String = "user"
    const val ESHOPE_PREFERENCES :String = "EshopPrefes"
    const val LOGGED_IN_USERNAME :String = "Logged_in_username"
    const val EXTRA_USER_DETAILS :String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1

    const val MALE :String = "male"
    const val FEMALE :String = "female"

    const val MOBILE:String = "mobile"
    const val GENDER :String = "gender"


    fun showImageChooser(activity: Activity){
        val gallerIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(gallerIntent, PICK_IMAGE_REQUEST_CODE)

    }

}