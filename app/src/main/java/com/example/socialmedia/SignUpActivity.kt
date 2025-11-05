package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        val signin_link_btn=findViewById<Button>(R.id.login_link_btn)
        val name=findViewById<EditText>(R.id.fullname_signup)
        val uname=findViewById<EditText>(R.id.username_signup)
        val email=findViewById<EditText>(R.id.email_signup)
        signin_link_btn.setOnClickListener {
            startActivity(Intent(this,SigninActivity::class.java))
            finish()
        }
    }
}