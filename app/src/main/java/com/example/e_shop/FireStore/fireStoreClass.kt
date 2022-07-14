package com.example.e_shop.FireStore

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.e_shop.Activities.LoginActivity
import com.example.e_shop.Activities.RegisterActivity
import com.example.e_shop.Activities.UserProfileActivity
import com.example.e_shop.models.User
import com.example.e_shop.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class fireStoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser (activity: RegisterActivity, userInfo: User){
        mFirestore.collection((Constants.USERS))

            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisterationSuccess()
            }
            .addOnFailureListener{
                e ->  activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }

    }

    private fun getCurrentUserID():String{
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""

        if(currentUser!=null){

            currentUserID = currentUser.uid
        }
        return currentUserID
    }


    @SuppressLint("LongLogTag")
    fun getUserDetails(activity: Activity){
        mFirestore.collection((Constants.USERS))

            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener {
                document->
                Log.i(javaClass.simpleName,document.toString())
                
                val user = document.toObject(User::class.java)


                val sharedPreferences = activity.getSharedPreferences(
                    Constants.ESHOPE_PREFERENCES,
                    Context.MODE_PRIVATE
                )


                val editor : SharedPreferences.Editor =sharedPreferences.edit()
                if (user != null) {
                    editor.putString(Constants.LOGGED_IN_USERNAME,
                        "${user.firstName} ${user.lastName}")

                }
                editor.apply()


                when(activity){
                    is LoginActivity ->{
                        if (user != null) {
                            activity.userLoggedInSuccess(user)
                        }
                    }
                }
            }

            }


    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String,Any>){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is UserProfileActivity ->{
                        activity.updateUserProfileSuccess()
                    }
                }
            }
            .addOnFailureListener{ e->
                when(activity){
                    is UserProfileActivity ->{
                        activity.hideProgressDialog()
                    }
                }
                Log.e (
                    activity.javaClass.simpleName,
                    "Error while updating user profile",
                    e
                        )

            }
    }
    }
