package com.buildbyte.npschamps.ui.initiative

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.buildbyte.npschamps.InitiativeData
import com.buildbyte.npschamps.R
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Initiative : Fragment() {

    private lateinit var initiativeLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_initiative, container, false)

        initiativeLayout = view.findViewById(R.id.initiativeLayout)

        fetchEventsFromFirestore()

        return view
    }

    private fun fetchEventsFromFirestore() {
        val db = Firebase.firestore
        val initiativeCollection = db.collection("initiative")

        initiativeCollection.get()
            .addOnSuccessListener { documents ->
                val initiatives = mutableListOf<InitiativeData>()
                for (document in documents) {
                    val title = document.getString("Title")
                    val description = document.getString("Description")
                    val imageResId = document.getString("Image")

                    if (title != null && description != null && imageResId != null) {
                        val initiative = InitiativeData(title, description, imageResId)
                        initiatives.add(initiative)
                    }
                }
                generateViews(initiatives)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    private fun generateViews(initiatives: List<InitiativeData>) {
        for (initiative in initiatives) {
            // Create a CardView programmatically
            val cardView = CardView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 16)
                }
                radius = 12f
                cardElevation = 6f
                setCardBackgroundColor(ContextCompat.getColor(context, R.color.teal_700))
            }

            // Create a LinearLayout to hold the CardView contents
            val linearLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(16, 16, 16, 16)
            }

            // Add ImageView
            val imageView = ImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    350
                ).apply {
                    setMargins(0, 0, 0, 12)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
                contentDescription = "Initiative Image"
            }
            // Use Glide to load the image from the URL
            Glide.with(requireContext())
                .load(initiative.imageResId)
                .into(imageView)
            linearLayout.addView(imageView)

            // Add TextView for Title
            val titleView = TextView(requireContext()).apply {
                text = initiative.title
                textSize = 20f
                setTypeface(null, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, android.R.color.white))
                setPadding(0, 0, 0, 8)
            }
            linearLayout.addView(titleView)

            // Add TextView for Description
            val descriptionView = TextView(requireContext()).apply {
                text = initiative.description
                textSize = 16f
                setTextColor(ContextCompat.getColor(context, android.R.color.white))
                setLineSpacing(4f, 1f)
            }
            linearLayout.addView(descriptionView)

            // Add the LinearLayout to the CardView
            cardView.addView(linearLayout)

            // Finally, add the CardView to the initiativeLayout
            initiativeLayout.addView(cardView)
        }
    }
}