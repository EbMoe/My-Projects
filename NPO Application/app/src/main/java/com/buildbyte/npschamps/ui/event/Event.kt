package com.buildbyte.npschamps.ui.event

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.buildbyte.npschamps.EventData
import com.buildbyte.npschamps.R
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 * Use the [Event.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Event : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var eventsGrid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        eventsGrid = view.findViewById(R.id.eventsGrid)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            fetchEventsFromFirestore()
            swipeRefreshLayout.isRefreshing = false
        }

        //we call the collection with the events inside of it
        fetchEventsFromFirestore()

        return view
    }

    private fun fetchEventsFromFirestore() {
        val db = Firebase.firestore
        val eventsCollection = db.collection("event")

        eventsCollection.orderBy("Date")
            .get()
            .addOnSuccessListener { documents ->
                val events = mutableListOf<EventData>()
                val currentTime = System.currentTimeMillis()
                for (document in documents) {
                    val id = document.id
                    val title = document.getString("Title") ?: ""
                    val dateTimestamp = document.getTimestamp("Date")
                    val date = dateTimestamp?.toDate()?.time?.let { java.sql.Timestamp(it) }
                    val location = document.getString("Location") ?: ""
                    val description = document.getString("Description") ?: ""
                    val imageResId = document.getString("Image") ?: ""

                    if (date != null && date.time >= currentTime) {
                        val event = EventData(id, title, date, location, description, imageResId)
                        Log.d("Event adding","$event added to list")
                        events.add(event)
                    }
                }
                // Check if the list is empty
                if (events.isEmpty()) {
                    displayNoUpcomingEventsMessage()
                } else {
                    // Once all the data is collected, we generate the views
                    Log.d("Event adding","Generating views with details $events")
                    generateViews(events)
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    private fun displayNoUpcomingEventsMessage() {
        val noEventsTextView = TextView(requireContext()).apply {
            text = "No upcoming events"
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.lightgreen))
            setPadding(16, 16, 16, 16)
            gravity = Gravity.CENTER
        }
        eventsGrid.addView(noEventsTextView)
    }

    private fun saveToDb(user: HashMap<String, String>, collectionPath: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.fragment_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        val db = Firebase.firestore
        val email = user["email"] ?: ""
        val phone = user["phone"] ?: ""

        // Check for existing entries with the same email or phone number
        db.collection(collectionPath)
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { emailDocuments ->
                if (!emailDocuments.isEmpty) {
                    Toast.makeText(requireContext(), "Entry already exists for this event", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    return@addOnSuccessListener
                }

                db.collection(collectionPath)
                    .whereEqualTo("phone", phone)
                    .get()
                    .addOnSuccessListener { phoneDocuments ->
                        if (!phoneDocuments.isEmpty) {
                            Toast.makeText(requireContext(), "Entry already exists for this event", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            return@addOnSuccessListener
                        }

                        // If no existing entry is found, add the new document
                        db.collection(collectionPath)
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                println("DocumentSnapshot added with ID: ${documentReference.id}")
                                Toast.makeText(requireContext(), "Successfully submitted", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                println("Error adding document $e")
                                Toast.makeText(requireContext(), "Error submitting, please try again later", Toast.LENGTH_SHORT).show()
                            }
                        dialog.dismiss()
                    }
                    .addOnFailureListener { e ->
                        println("Error checking phone number $e")
                        Toast.makeText(requireContext(), "Error checking phone number, please try again later", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
            }
            .addOnFailureListener { e ->
                println("Error checking email $e")
                Toast.makeText(requireContext(), "Error checking email, please try again later", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
    }

    private fun generateViews(events: List<EventData>) {
        eventsGrid.removeAllViews() // Clear any existing views

        for ((index, event) in events.withIndex()) {
            val cardView = CardView(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = GridLayout.LayoutParams.MATCH_PARENT
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(index)
                    setMargins(16, 16, 16, 16)
                }
                radius = 12f
                cardElevation = 6f
            }

            val linearLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val imageView = ImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 350
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            // Use Glide to load the image from the URL with error handling
            Glide.with(requireContext())
                .load(event.imageResId)
                .error(R.drawable.placehold) // Placeholder image in case of error
                .into(imageView)
            linearLayout.addView(imageView)

            val titleView = TextView(requireContext()).apply {
                text = event.title
                textSize = 18f
                setTypeface(null, Typeface.BOLD)
                setTextColor(ContextCompat.getColor(context, R.color.lightgreen))
                setPadding(8, 8, 8, 8)
            }
            linearLayout.addView(titleView)

            val dateView = TextView(requireContext()).apply {
                text = event.date.toString()
                textSize = 14f
                setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                setPadding(8, 8, 8, 8)
            }
            linearLayout.addView(dateView)

            val descriptionScrollView = ScrollView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    200 // Set a maximum height for the description view
                )
            }

            val descriptionView = TextView(requireContext()).apply {
                text = event.description
                textSize = 14f
                setPadding(8, 8, 8, 8)
            }
            descriptionScrollView.addView(descriptionView)
            linearLayout.addView(descriptionScrollView)

            val contextView = TextView(requireContext()).apply {
                text = "Click to volunteer"
                textSize = 14f
                setTextColor(ContextCompat.getColor(context, R.color.lightgreen))
                setPadding(8, 8, 8, 8)
            }
            linearLayout.addView(contextView)

            cardView.addView(linearLayout)
            cardView.setOnClickListener {
                submitVolunteer(event)
            }
            eventsGrid.addView(cardView)
        }
    }

    //user inputs their details here to participate in an event
    private fun submitVolunteer(event: EventData) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.fragment_volunteer_form)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val title = dialog.findViewById<TextView>(R.id.volunteerEventTitle)
        val firstName = dialog.findViewById<EditText>(R.id.firstName)
        val lastName = dialog.findViewById<EditText>(R.id.lastName)
        val email = dialog.findViewById<EditText>(R.id.email)
        val phone = dialog.findViewById<EditText>(R.id.telephone)
        val submit = dialog.findViewById<Button>(R.id.submitVolunteer)

        title.text = event.title

        submit.setOnClickListener {
            try {
                val firstNameText = firstName.text.toString()
                val lastNameText = lastName.text.toString()
                val emailText = email.text.toString()
                val phoneText = phone.text.toString()

                if (validateUserData(firstNameText, lastNameText, emailText, phoneText)) {
                    val user = hashMapOf(
                        "firstName" to firstNameText,
                        "lastName" to lastNameText,
                        "email" to emailText,
                        "phone" to phoneText
                    )

                    saveToDb(user, "event/${event.id}/volunteer")
                    dialog.dismiss()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "error $e", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun validateUserData(firstName: String, lastName: String, email: String, phone: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return when {

            firstName.isBlank() -> {
                Toast.makeText(requireContext(), "First name cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            lastName.isBlank() -> {
                Toast.makeText(requireContext(), "Last name cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            email.isBlank() || !email.matches(emailPattern.toRegex()) -> {
                Toast.makeText(requireContext(), "Invalid Email address", Toast.LENGTH_SHORT).show()
                false
            }
            phone.isBlank() || phone.length < 9 || phone.length > 12 -> {
                Toast.makeText(requireContext(), "Invalid Phone number", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }


    //This is all dummy code but I don't want to risk a coconut.jpg issue so I am leaving it
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Event.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Event().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

