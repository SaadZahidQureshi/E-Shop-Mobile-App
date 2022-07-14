package com.example.e_shop.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.e_shop.FireStore.fireStoreClass
import com.example.e_shop.R
import com.example.e_shop.models.User
import com.example.e_shop.utils.Constants
import com.example.e_shop.utils.ESEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestoreSettings


class LoginActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide()

        //Launch the register activity here
//        var tv_register :TextView =findViewById(R.id.tv_register)
//        tv_register.setOnClickListener{
//            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
//            startActivity(intent)
//           // finish()
//        }

        val tv_forgot_password = findViewById<TextView>(R.id.tv_forgot_password)
        val tv_register = findViewById<TextView>(R.id.tv_register)
        val btn_login = findViewById<Button>(R.id.btn_login)

        tv_forgot_password.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    fun userLoggedInSuccess(user: User){
        hideProgressDialog()

        Log.i ("First Name : ",user.firstName)
        Log.i ("Last Name : ",user.lastName)
        Log.i ("Email : ",user.email)

        if (user.profileCompleted == 0){
            val intent = Intent (this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }
        else{
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

        }
        finish()
    }

    override fun onClick(view:View?){
        if(view != null){
            when(view.id){
                R.id.tv_forgot_password->{
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.tv_register->{
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login->{
                    loginRegisteredUser()
                }

            }
        }

    }

    private fun validateLoginDetails() : Boolean{

        val et_email : ESEditText = findViewById(R.id.et_email)
        val et_password : ESEditText = findViewById(R.id.et_password)

        return when{


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

            else -> {true}
        }

    }

    private fun loginRegisteredUser(){
        if (validateLoginDetails()){
            showProgressDialog()

            val et_email : ESEditText = findViewById(R.id.et_email)
            val et_password : ESEditText = findViewById(R.id.et_password)

            val email = et_email.text.toString().trim{ it <= ' '}
            val password = et_password.text.toString().trim{ it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if(task.isSuccessful){
                        fireStoreClass().getUserDetails(this@LoginActivity)                    }
                    else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }

    }



}