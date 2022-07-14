package com.example.e_shop.Activities


import android.os.Bundle

import android.widget.Toast
import com.example.e_shop.R
import com.example.e_shop.utils.ESButton
import com.example.e_shop.utils.ESEditText
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        getSupportActionBar()?.hide()
        val actionbar = supportActionBar
                if(actionbar != null) {
                    actionbar.setDisplayHomeAsUpEnabled(true)
                }

        val btn_submit : ESButton = findViewById(R.id.btn_submit)
        btn_submit.setOnClickListener(){
            validateForgotPasswordDetials()
//            var et_email :ESEditText = findViewById(R.id.et_email)
//            validateRegisterDetials()
//            validateEmailAddress(et_email)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun validateForgotPasswordDetials(){


        val et_email :ESEditText = findViewById(R.id.et_email_forgot_password)
        val email :String = et_email.text.toString().trim{ it <= ' '}

        if(email.isEmpty() ){
            showErrorSnackBar(resources.getString(R.string.error_msg_enter_email),
                false)
        }
        else
        {
            showProgressDialog()
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener{
                    task->
                    hideProgressDialog()
                    if(task.isSuccessful){
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            resources.getString(R.string.email_sent_success),
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    }
                    else{
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }
    }

}