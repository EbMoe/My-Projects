package com.buildbyte.npschamps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.buildbyte.npschamps.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load fade-in animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.splashLogo.startAnimation(fadeIn)
        binding.splashText.startAnimation(fadeIn)

        // Delay for 2 seconds, then transition to MainActivity
        Handler().postDelayed({
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
