package com.example.e_shop.Activities


import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.e_shop.FireStore.fireStoreClass
import com.example.e_shop.R
import com.example.e_shop.models.User
import com.example.e_shop.utils.ESButton
import com.example.e_shop.utils.ESEditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide() // hide action and ttile bar
        //following code use for full screen view of splash screen
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //Launch the Login activity here
        val tv_login: TextView = findViewById<TextView>(R.id.tv_login)
        tv_login.setOnClickListener {
            onBackPressed()
        }



        //backpressed to go to previous activity
        val actionbar = supportActionBar
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }


        val btn_register :ESButton = findViewById(R.id.btn_register)
        btn_register.setOnClickListener(){
            registerUser()
//            var et_email :ESEditText = findViewById(R.id.et_email)
//            validateRegisterDetials()
//            validateEmailAddress(et_email)
        }

    }


    //backpressed to go to previous activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


private fun validateRegisterDetials(): Boolean{

    val et_first_name :ESEditText = findViewById(R.id.et_first_name)
    val et_last_name :ESEditText = findViewById(R.id.et_last_name)
    val et_email :ESEditText = findViewById(R.id.et_email)
    val et_password :ESEditText = findViewById(R.id.et_password)
    val et_confirm_password :ESEditText = findViewById(R.id.et_confirm_password)
    val cb_terms_and_conditions :CheckBox = findViewById(R.id.cb_terms_and_conditions)


    return when{
        TextUtils.isEmpty(et_first_name.text.toString().trim{ it <= ' '}) ->
        {
            showErrorSnackBar(resources.getString(R.string.error_msg_enter_first_name),true)
            false
        }

        TextUtils.isEmpty(et_last_name.text.toString().trim{ it <= ' '}) ->
        {
            showErrorSnackBar(resources.getString(R.string.error_msg_enter_last_name),true)
            false
        }

        TextUtils.isEmpty( et_email.text.toString().trim{ it <= ' '}) ->
        {
            showErrorSnackBar(resources.getString(R.string.error_msg_enter_email),true)
            false

        }

        TextUtils.isEmpty(et_password.text.toString().trim{ it <= ' '}) ->
        {
            showErrorSnackBar(resources.getString(R.string.error_msg_enter_password),true)
            false
        }

        TextUtils.isEmpty(et_confirm_password.text.toString().trim{ it <= ' '}) ->
        {
            showErrorSnackBar(resources.getString(R.string.error_msg_enter_confirm_password),true)
            false
        }

        et_password.text.toString().trim{ it <= ' '}  != et_confirm_password.text.toString().trim{ it <= ' '} ->
        {
            showErrorSnackBar(resources.getString(R.string.error_msg_passwor_and_confirm_password_mismatch),true)
            false
        }

        !cb_terms_and_conditions.isChecked->
        {
            showErrorSnackBar(resources.getString(R.string.error_msg_agree_terms_conditions),true)
            false
        }
        else ->{
//            showErrorSnackBar(resources.getString(R.string.registery_successfull),false)
            true
        }
    }
}

//    private fun validateEmailAddress(email : ESEditText):Boolean{
//        var emailinput = email.text.toString()
//        print(emailinput)
//
//        if(!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){
//            Toast.makeText(this,"Email Validated successfull", Toast.LENGTH_SHORT).show()
//            return true
//        }
//        else{
//            showErrorSnackBar(resources.getString(R.string.error_msg_enter_email),true)
//            return false
//        }
//    }
        private fun registerUser(){
            if(validateRegisterDetials()){

                showProgressDialog()
                val et_email :ESEditText = findViewById(R.id.et_email)
                val et_password :ESEditText = findViewById(R.id.et_password)

                val email :String = et_email.text.toString().trim{ it <= ' '}
                val password :String = et_password.text.toString().trim{ it <= ' '}


                val et_first_name :ESEditText = findViewById(R.id.et_first_name)
                val et_last_name :ESEditText = findViewById(R.id.et_last_name)


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(
                        OnCompleteListener { task ->

                            if (task.isSuccessful){
                                val firebaseUser : FirebaseUser = task.result!!.user!!

                                val user = User(
                                    firebaseUser.uid,
                                    et_first_name.text.toString().trim{ it <= ' '},
                                    et_last_name.text.toString().trim{ it <= ' '},
                                    et_email.text.toString().trim{ it <= ' '}
                                )

                                fireStoreClass().registerUser(this@RegisterActivity,user)

//                                showErrorSnackBar(
//                                    "yoy registered successfully. you userID is ${firebaseUser.uid}",
//                                    false)
//                                FirebaseAuth.getInstance().signOut()
//                                finish()
                            }
                            else{
                                hideProgressDialog()
                                showErrorSnackBar(task.exception!!.message.toString(),true)
                            }
                        }
                    )
            }
        }

    fun userRegisterationSuccess(){
        hideProgressDialog()
        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()
    }

    }


