package com.alpha.eventplanner

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.alpha.eventplanner.Event
import com.alpha.eventplanner.R
import java.util.*

class EditEventDialogFragment(
    private val event: Event,
    private val onSaveClick: (Event) -> Unit
) : DialogFragment() {

    private lateinit var eventTitleInput: EditText
    private lateinit var dateTimeInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var saveButton: Button
    private var selectedDateTime: Calendar = Calendar.getInstance() // Calendar to hold the selected date and time

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_edit_event, container, false)

        // Initialize UI elements
        eventTitleInput = view.findViewById(R.id.editEventTitleInput)
        dateTimeInput = view.findViewById(R.id.editDateTimeInput)
        locationInput = view.findViewById(R.id.editLocationInput)
        saveButton = view.findViewById(R.id.saveButton)

        // Set existing event details
        eventTitleInput.setText(event.title)
        dateTimeInput.setText(event.dateTime)
        locationInput.setText(event.location)

        // Date and Time Picker setup
        dateTimeInput.setOnClickListener {
            showDateTimePicker()
        }

        // Set onClickListener for the save button
        saveButton.setOnClickListener {
            val updatedTitle = eventTitleInput.text.toString()
            val updatedDateTime = dateTimeInput.text.toString()
            val updatedLocation = locationInput.text.toString()

            // Check if all fields are filled
            if (updatedTitle.isNotEmpty() && updatedDateTime.isNotEmpty() && updatedLocation.isNotEmpty()) {
                val updatedEvent = event.copy(
                    title = updatedTitle,
                    dateTime = updatedDateTime,
                    location = updatedLocation
                )
                onSaveClick(updatedEvent) // Pass the updated event to the callback
                dismiss() // Close the dialog
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set dialog to match the parent width and wrap the content height
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    // Function to show date and time picker
    private fun showDateTimePicker() {
        // Get the current date
        val currentDate = Calendar.getInstance()

        // DatePickerDialog to select the date
        DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                // Set the selected date in the Calendar object
                selectedDateTime.set(year, monthOfYear, dayOfMonth)

                // After the date is selected, show TimePickerDialog
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        // Set the selected time in the Calendar object
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDateTime.set(Calendar.MINUTE, minute)

                        // Format the selected date and time and set it to the EditText
                        val formattedDateTime = String.format(
                            "%02d/%02d/%04d %02d:%02d",
                            dayOfMonth, monthOfYear + 1, year, hourOfDay, minute
                        )
                        dateTimeInput.setText(formattedDateTime)

                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    true
                ).show()

            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
