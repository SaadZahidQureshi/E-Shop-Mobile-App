package com.example.e_shop.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User (

    val id : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
    val image : String = "",
    val gender : String = "",
    val mobile : Long = 0,
    val profileCompleted : Int = 0,


):Parcelable