package com.alpha.eventplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        // Initialize toggle listeners for each section title
        findViewById<TextView>(R.id.section1Title).setOnClickListener { toggleSection(R.id.section1Content) }
        findViewById<TextView>(R.id.section2Title).setOnClickListener { toggleSection(R.id.section2Content) }
        findViewById<TextView>(R.id.section3Title).setOnClickListener { toggleSection(R.id.section3Content) }
        findViewById<TextView>(R.id.section4Title).setOnClickListener { toggleSection(R.id.section4Content) }
        findViewById<TextView>(R.id.section5Title).setOnClickListener { toggleSection(R.id.section5Content) }
    }

    // Method to toggle visibility of a section's content
    private fun toggleSection(sectionContentId: Int) {
        val contentTextView = findViewById<TextView>(sectionContentId)
        if (contentTextView.visibility == View.GONE) {
            contentTextView.visibility = View.VISIBLE
        } else {
            contentTextView.visibility = View.GONE
        }
    }
}
