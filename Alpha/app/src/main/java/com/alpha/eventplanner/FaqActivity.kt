package com.alpha.eventplanner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FaqActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        // Initialize all the toggle functions for FAQs
        findViewById<TextView>(R.id.question1).setOnClickListener { toggleAnswer(R.id.answer1) }
        findViewById<TextView>(R.id.question2).setOnClickListener { toggleAnswer(R.id.answer2) }
        findViewById<TextView>(R.id.question3).setOnClickListener { toggleAnswer(R.id.answer3) }
        findViewById<TextView>(R.id.question4).setOnClickListener { toggleAnswer(R.id.answer4) }
        findViewById<TextView>(R.id.question5).setOnClickListener { toggleAnswer(R.id.answer5) }
        findViewById<TextView>(R.id.question6).setOnClickListener { toggleAnswer(R.id.answer6) }
    }

    // Function to toggle visibility of the answer
    private fun toggleAnswer(answerViewId: Int) {
        val answerTextView = findViewById<TextView>(answerViewId)
        if (answerTextView.visibility == View.GONE) {
            answerTextView.visibility = View.VISIBLE
        } else {
            answerTextView.visibility = View.GONE
        }
    }
}
