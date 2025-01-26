package com.alpha.eventplanner

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class AboutActivity : AppCompatActivity() {

    // Declare the TextView variables for expandable content
    private lateinit var aboutDescription: TextView
    private lateinit var featuresDescription: TextView
    private lateinit var footerContact: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Initialize the ImageView for the logo
        val imageView = findViewById<ImageView>(R.id.appLogo)

        // Load and start the fade-in animation for the logo
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        imageView.startAnimation(fadeInAnimation)

        // Initialize Views for the expandable content
        aboutDescription = findViewById(R.id.aboutDescription)
        featuresDescription = findViewById(R.id.featuresDescription)
        footerContact = findViewById(R.id.footerContact)

        // Set click listeners for expandable card views
        findViewById<MaterialCardView>(R.id.aboutCard).setOnClickListener {
            toggleVisibility(aboutDescription)
        }

        findViewById<MaterialCardView>(R.id.featuresCard).setOnClickListener {
            toggleVisibility(featuresDescription)
        }

        findViewById<MaterialCardView>(R.id.contactCard).setOnClickListener {
            toggleVisibility(footerContact)
        }
    }

    // Function to toggle visibility between VISIBLE and GONE
    private fun toggleVisibility(textView: TextView) {
        if (textView.visibility == View.VISIBLE) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }
    }
}
