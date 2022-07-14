package com.example.e_shop.Activities

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import com.example.e_shop.R
import com.example.e_shop.utils.Constants

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionbar = supportActionBar
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        val sharedPreferences = getSharedPreferences(Constants.ESHOPE_PREFERENCES,Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!
        Log.i ("Hello ",username)
        var tv_main :TextView = findViewById(R.id.tv_main)
        tv_main.text = "Hello $username."
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}