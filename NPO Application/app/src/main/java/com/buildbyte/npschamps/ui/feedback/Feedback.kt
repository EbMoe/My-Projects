package com.buildbyte.npschamps.ui.feedback

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.buildbyte.npschamps.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Feedback : Fragment() {

    companion object {
        fun newInstance() = Feedback()
    }

    private lateinit var viewModel: FeedbackViewModel
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var telephone: EditText
    private lateinit var feedback: EditText
    private lateinit var submit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_feedback, container, false)

        firstName = view.findViewById(R.id.feedback_first_name)
        lastName = view.findViewById(R.id.feedback_last_name)
        email = view.findViewById(R.id.feedback_email)
        telephone = view.findViewById(R.id.feedback_telephone)
        feedback = view.findViewById(R.id.feedback_comment)
        submit = view.findViewById(R.id.feedback_submit)

        //when the button is clicked we save the data
        submit.setOnClickListener {
            val firstNameText = firstName.text.toString()
            val lastNameText = lastName.text.toString()
            val emailText = email.text.toString()
            val telephoneText = telephone.text.toString()
            val feedbackText = feedback.text.toString()

            if (validateFeedbackData(firstNameText, lastNameText, emailText, telephoneText, feedbackText)) {
                val user = hashMapOf(
                    "firstName" to firstNameText,
                    "lastName" to lastNameText,
                    "email" to emailText,
                    "telephone" to telephoneText,
                    "feedback" to feedbackText
                )

                saveToFirestore(user)
            }
        }

        return view
    }

    //Save the feedback to Firestore
    private fun saveToFirestore(user: HashMap<String, String>) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.fragment_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
        
        val db = Firebase.firestore

        db.collection("feedback")
            .add(user)
            .addOnSuccessListener {
                firstName.text.clear()
                lastName.text.clear()
                email.text.clear()
                telephone.text.clear()
                feedback.text.clear()
                Toast.makeText(requireContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error submitting, please try again later", Toast.LENGTH_SHORT).show()
            }
        dialog.dismiss()
    }

    private fun validateFeedbackData(firstName: String, lastName: String, email: String, telephone: String, feedback: String): Boolean {
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
                Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show()
                false
            }
            telephone.isBlank() || telephone.length < 9 || telephone.length > 12-> {
                Toast.makeText(requireContext(), "Invalid Telephone Number", Toast.LENGTH_SHORT).show()
                false
            }
            feedback.isBlank() -> {
                Toast.makeText(requireContext(), "Feedback cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        // TODO: Use the ViewModel
    }

}