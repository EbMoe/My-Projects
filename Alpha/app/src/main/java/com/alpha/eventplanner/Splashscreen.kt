package com.alpha.eventplanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.alpha.eventplanner.R
import com.google.firebase.auth.FirebaseAuth

class Splashscreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Find the ImageView (for future animation)
        val imageView = findViewById<ImageView>(R.id.imageView)

        // Load the fade-in animation
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Start the animation on the ImageView
        imageView.startAnimation(fadeInAnimation)

        // Set up a delay for the splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
        }, 2000) // 2000 milliseconds (2 seconds) delay
    }

    private fun checkLoginStatus() {
        // Retrieve login status from SharedPreferences
        val sharedPreferences = getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (auth.currentUser != null && isLoggedIn) {
            // User is logged in, navigate to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // User is not logged in, navigate to LoginActivity or RegisterActivity
            navigateToRegister()
        }
        finish() // Close the Splashscreen activity
    }

    private fun navigateToRegister() {
        // Navigate to RegisterActivity
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}
