package com.alpha.alphadelivery

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.alpha.alphadelivery.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the animations (fade-in and scale-up)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Apply the animations to the logo
        binding.splashLogo.startAnimation(fadeIn)

        // Transition to MainActivity after animation
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // Adjust the delay to match the animation duration
    }
}
