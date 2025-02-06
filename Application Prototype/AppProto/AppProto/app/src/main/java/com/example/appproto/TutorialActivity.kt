package com.example.appproto

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        // Load the fade-in animation
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein)

        // Apply the animation to each TextView
        val introText = findViewById<TextView>(R.id.intro_text)
        val feature1Text = findViewById<TextView>(R.id.feature1_text)
        val feature2Text = findViewById<TextView>(R.id.feature2_text)
        val feature3Text = findViewById<TextView>(R.id.feature3_text)
        val feature4Text = findViewById<TextView>(R.id.feature4_text)

        introText.startAnimation(fadeInAnimation)
        feature1Text.startAnimation(fadeInAnimation)
        feature2Text.startAnimation(fadeInAnimation)
        feature3Text.startAnimation(fadeInAnimation)
        feature4Text.startAnimation(fadeInAnimation)

        val btnShowGallery = findViewById<Button>(R.id.btn_show_images)
        btnShowGallery.setOnClickListener {
            val intent = Intent(this, ViewGallery::class.java)
            startActivity(intent)
        }

        }
    }

