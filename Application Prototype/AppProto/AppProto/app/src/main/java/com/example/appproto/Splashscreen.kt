package com.example.appproto

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appproto.MainActivity

class Splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        // Load the fade-in animation
        val fadeInSplashAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein)

        // Find the ImageView
        val imageView = findViewById<ImageView>(R.id.imageView)

        // Load the GIF into the ImageView using Glide
        Glide.with(this)
            .load(R.drawable.alpha2) // Replace 'your_gif_name' with your GIF file name in the drawable folder
            .into(imageView)

        // Check if user is logged in
        val userLoggedIn = checkIfUserLoggedIn()

        // Determine the next activity based on login status
        val nextActivity = if (userLoggedIn) {
            MainActivity::class.java
        } else {
            RegisterActivity::class.java
        }

        // Start the next activity after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, nextActivity))
            finish()
        }, 5000) // 5000 is the delayed time in milliseconds.
    }

    // Function to check if the user is logged in
    private fun checkIfUserLoggedIn(): Boolean {
        // Retrieve the login status from SharedPreferences
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("logged_in", false)
    }

    // Function to set the login status in SharedPreferences
    private fun setLoginStatus(loggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("logged_in", loggedIn).apply()
    }
}
