package com.example.socialmedia

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        // Button click listener

        val signup_btn=findViewById<Button>(R.id.signup_btn)
        signup_btn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val fullname=findViewById<EditText>(R.id.fullname_signup)
        val fullName = fullname.text.toString()
        val signup_username=findViewById<EditText>(R.id.username_signup)
        val username = signup_username.text.toString()
        val signup_email=findViewById<EditText>(R.id.email_signup)
        val email = signup_email.text.toString()
        val signup_password=findViewById<EditText>(R.id.password_signup)
        val password = signup_password.text.toString()

        // Validation
        when {
            TextUtils.isEmpty(fullName) -> showToast("Full name is required")
            TextUtils.isEmpty(username) -> showToast("Username is required")
            TextUtils.isEmpty(email) -> showToast("Email is required")
            TextUtils.isEmpty(password) -> showToast("Password is required")
            else -> {
                // Show progress
                progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Signup")
                progressDialog.setMessage("Please wait, this may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                // Firebase Signup
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUserInfo(fullName, username, email)
                        } else {
                            showToast("Error: " + task.exception.toString())
                            auth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullName: String, username: String, email: String) {
        val currentUserId = auth.currentUser?.uid ?: ""
        val userRef = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullname"] = fullName
        userMap["username"] = username
        userMap["email"] = email
        userMap["bio"] = "Hey I am using Social Media."
        userMap["image"] = "DEFAULT_IMAGE_URL"

        userRef.child(currentUserId)
            .setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    showToast("Account has been created successfully.")
                    // Go to MainActivity
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Error: " + task.exception.toString())
                    auth.signOut()
                    progressDialog.dismiss()
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_LONG).show()
    }
}