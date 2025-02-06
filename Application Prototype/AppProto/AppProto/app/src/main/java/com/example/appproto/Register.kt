package com.example.appproto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    // Get reference to Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        registerButton = findViewById(R.id.btn_register)
        loginButton = findViewById(R.id.btn_login)

        // Set click listener for the register button
        registerButton.setOnClickListener {
            // Retrieve email, password, and confirm password from EditTexts
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Check if password and confirm password match
            if (password != confirmPassword) {
                Toast.makeText(baseContext, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if user already exists
            auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val signInMethods = task.result?.signInMethods
                        if (signInMethods != null && signInMethods.isNotEmpty()) {
                            // User already exists, prompt to login
                            Toast.makeText(baseContext, "User already exists. Please login.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // User does not exist, proceed with registration
                            registerUser(email, password)
                        }
                    } else {
                        // Error fetching sign-in methods
                        Toast.makeText(baseContext, "Error checking user existence.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Set click listener for the login button
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success
                    Toast.makeText(baseContext, "Registration successful.", Toast.LENGTH_SHORT).show()

                    // Start LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                    // Finish RegisterActivity
                    finish()
                } else {
                    // Registration failed
                    Toast.makeText(baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
