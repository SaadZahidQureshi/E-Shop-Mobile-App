package com.example.e_shop.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.e_shop.R
import com.example.e_shop.utils.ESButton
import com.example.e_shop.utils.ESEditText
import com.example.e_shop.utils.ESTextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
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
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



        //backpressed to go to previous activity
        val actionbar = supportActionBar
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }


        var btn_register :ESButton = findViewById(R.id.btn_register)
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

    var et_first_name :ESEditText = findViewById(R.id.et_first_name)
    var et_last_name :ESEditText = findViewById(R.id.et_last_name)
    var et_email :ESEditText = findViewById(R.id.et_email)
    var et_password :ESEditText = findViewById(R.id.et_password)
    var et_confirm_password :ESEditText = findViewById(R.id.et_confirm_password)
    var cb_terms_and_conditions :CheckBox = findViewById(R.id.cb_terms_and_conditions)


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

        TextUtils.isEmpty( et_email.text.toString().trim{ it <= ' '}) || Patterns.EMAIL_ADDRESS.matcher(et_email.toString()).matches() ->
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
                var et_email :ESEditText = findViewById(R.id.et_email)
                var et_password :ESEditText = findViewById(R.id.et_password)

                val email :String = et_email.text.toString().trim{ it <= ' '}
                val password :String = et_password.text.toString().trim{ it <= ' '}

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(
                        OnCompleteListener { task ->
                            hideProgressDialog()
                            if (task.isSuccessful){
                                val firebaseUser : FirebaseUser = task.result!!.user!!
                                showErrorSnackBar(
                                    "yoy registered successfully. you userID is ${firebaseUser.uid}",
                                    false)
                            }
                            else{
                                showErrorSnackBar(task.exception!!.message.toString(),true)
                            }
                        }
                    )
            }
        }

    }


