package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()

        lo

            .setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = login_email.text.toString().trim()
        val password = login_password.text.toString().trim()

        when {
            email.isBlank() -> showToast("Email is required")
            password.isBlank() -> showToast("Password is required")
            else -> {
                showLoading(true)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        showLoading(false)
                        if (task.isSuccessful) {
                            showToast("Logged in successfully!")
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            showToast("Error: ${task.exception?.message}")
                        }
                    }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            if (loadingDialog == null) {
                loadingDialog = AlertDialog.Builder(this)
                    .setView(ProgressBar(this))
                    .setCancelable(false)
                    .create()
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }
}
