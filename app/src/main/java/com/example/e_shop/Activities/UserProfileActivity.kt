package com.example.e_shop.Activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.e_shop.FireStore.fireStoreClass
import com.example.e_shop.R
import com.example.e_shop.models.User
import com.example.e_shop.utils.Constants
import com.example.e_shop.utils.GlideLoader
import com.google.firebase.auth.ktx.userProfileChangeRequest
import java.io.IOException
import java.util.jar.Manifest

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var muserDetails : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        getSupportActionBar()?.hide()


        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            muserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }


        val et_first_name:EditText = findViewById<EditText>(R.id.et_first_name)
        et_first_name.isEnabled = false
        et_first_name.setText(muserDetails.firstName)

        val et_last_name:EditText = findViewById<EditText>(R.id.et_last_name)
        et_last_name.isEnabled = false
        et_last_name.setText(muserDetails.lastName)

        val et_email:EditText = findViewById<EditText>(R.id.et_email)
        et_email.isEnabled = false
        et_email.setText(muserDetails.email)

        val iv_user_image:ImageView = findViewById<ImageView>(R.id.iv_user_image)
        iv_user_image.setOnClickListener(this@UserProfileActivity)

        val btn_submit : Button = findViewById<Button>(R.id.btn_submit)
        btn_submit.setOnClickListener(this@UserProfileActivity)

        //backpressed to go to previous activity
        val actionbar = supportActionBar
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }
    //backpressed to go to previous activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(view: View?) {
//        var iv_user_image:ImageView = findViewById<ImageView>(R.id.iv_user_image)
//        var et_last_name:EditText = findViewById<EditText>(R.id.et_last_name)
//        var et_last_name:EditText = findViewById<EditText>(R.id.et_last_name)


         if(view != null){
             when (view.id){
                 R.id.iv_user_image ->{
                     if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                     == PackageManager.PERMISSION_GRANTED){
                         //showErrorSnackBar("You already have the storage permissions.",false)
                         Constants.showImageChooser(this)
                     }else{
                         ActivityCompat.requestPermissions(
                             this,
                             arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                             Constants.READ_STORAGE_PERMISSION_CODE
                         )
                     }


                 }

                 R.id.btn_submit ->{
                     if(validUserProfileDetails() ){

                         val userHashMap =HashMap<String, Any>()

                         var et_mobile_number:EditText = findViewById<EditText>(R.id.et_mobile_number)
                         val mobile_number = et_mobile_number.text.toString().trim{it <= ' '}

                         var rb_male:RadioButton = findViewById<RadioButton>(R.id.rb_male)
//                         var rb_female:RadioButton = findViewById<RadioButton>(R.id.rb_female)

                         val gender = if(rb_male.isChecked){
                             Constants.MALE
                         }else{
                             Constants.FEMALE
                         }

                         if(mobile_number.isNotEmpty()){
                             userHashMap[Constants.MOBILE] = mobile_number.toLong()
                         }
                         userHashMap[Constants.GENDER] = gender

                         showProgressDialog()

                         fireStoreClass().updateUserProfileData(this,userHashMap)






//                         showErrorSnackBar(resources.getString(R.string.your_details_are_valid),false)
                     }
                 }
             }
         }
    }

    fun updateUserProfileSuccess(){
        hideProgressDialog()

        Toast.makeText(this, resources.getString(R.string.your_profile_updated_successfully), Toast.LENGTH_SHORT).show()

        startActivity(Intent(this@UserProfileActivity, MainActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //showErrorSnackBar("the storage permission is granted.",false)
                Constants.showImageChooser(this)
            }else{
                Toast.makeText(this,resources.getString(R.string.read_storage_permission_denied),Toast.LENGTH_LONG).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(data != null){
                try {
                    val selectedImageFileURI = data.data!!
                    var iv_user_image:ImageView = findViewById<ImageView>(R.id.iv_user_image)
//                    iv_user_image.setImageURI(Uri.parse(selectedImageFileURI.toString()))
                    GlideLoader(this).loadUserPicture(selectedImageFileURI,iv_user_image)

                }
                catch (e : IOException){
                    e.printStackTrace()
                    Toast.makeText(this, resources.getString(R.string.image_selection_failed),Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if(resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(this, resources.getString(R.string.data_cannot_be_null),Toast.LENGTH_SHORT).show()

        }
    }

    private fun validUserProfileDetails(): Boolean{
        val et_mobile_number : EditText = findViewById<EditText>(R.id.et_mobile_number)
        return when{
            TextUtils.isEmpty(et_mobile_number.text.toString().trim{it <= ' '})->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number),true)
                false
            }
            else ->{
                 true
            }
        }
    }
}