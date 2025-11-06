package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        val signup_link_btn=findViewById<Button>(R.id.signup_link_btn)
        signup_link_btn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }
}
