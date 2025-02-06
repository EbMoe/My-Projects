package com.example.appproto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar  // Declare a ProgressBar

    // Get reference to Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.login_progress)  // Initialize the ProgressBar

        // Set click listener for the login button
        loginButton.setOnClickListener {
            // Show ProgressBar when login is initiated
            progressBar.visibility = View.VISIBLE
            // Retrieve email and password from EditTexts
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform login logic here
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                // Hide ProgressBar when login attempt is complete
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Login success
                    Toast.makeText(baseContext, "Login successful.", Toast.LENGTH_SHORT).show()
                    navigateToMainActivity()
                } else {
                    // Login failed
                    Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)  // Smooth transition
    }
}
