package com.example.appproto

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat

import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner
    lateinit var edName: EditText
    lateinit var edDesc: EditText
    lateinit var btnStartDate: Button
    lateinit var btnEndDate: Button
    lateinit var btnStartTime: Button
    lateinit var btnEndTime: Button
    lateinit var btnSave: Button
    lateinit var btnImageNav: Button // Navigate to the camera activity
    lateinit var btnAdvCamera: Button // New advanced button for camera
    lateinit var btnReadAll: Button // Action to read all reports into database
    lateinit var edMinGoal: EditText // EditText for minimum daily goal
    lateinit var edMaxGoal: EditText // EditText for maximum daily goal
    lateinit var btnTutorial: Button // Navigate to tutorial activity
    lateinit var btnShare: ImageButton // Button to share task details
    lateinit var btnAbout: ImageButton

    // Globals
    var startDate: Date? = null
    var startTime: Date? = null
    var endDate: Date? = null
    var endTime: Date? = null
    // Firebase reference
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        spinner = findViewById(R.id.spinner)
        edName = findViewById(R.id.edName)
        edDesc = findViewById(R.id.edDesc)
        btnStartDate = findViewById(R.id.btnStartDate)
        btnEndDate = findViewById(R.id.btnEndDate)
        btnStartTime = findViewById(R.id.btnStartTime)
        btnEndTime = findViewById(R.id.btnEndTime)
        btnSave = findViewById(R.id.btnSave)
        btnAdvCamera = findViewById(R.id.btnCameraTwo)
        btnReadAll = findViewById(R.id.btnRead)
        edMinGoal = findViewById(R.id.edMinGoal)
        edMaxGoal = findViewById(R.id.edMaxGoal)
        btnTutorial = findViewById(R.id.btnTutorial)
        btnShare = findViewById(R.id.btnShare)
        btnAbout = findViewById(R.id.btnAbout)

        database = FirebaseDatabase.getInstance().reference
        spinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_dropdown_item
        )
    }

    private fun setupListeners() {
        btnStartDate.setOnClickListener { showDatePicker(startDateListener) }
        btnEndDate.setOnClickListener { showDatePicker(endDateListener) }
        btnStartTime.setOnClickListener { showTimePicker(startTimeListener) }
        btnEndTime.setOnClickListener { showTimePicker(endTimeListener) }
        btnSave.setOnClickListener { saveTask() }
        btnAdvCamera.setOnClickListener {
            startActivity(Intent(this, camera2::class.java)) // Ensure you have this activity created
        }
        btnReadAll.setOnClickListener { fetchAndDisplay() }
        btnTutorial.setOnClickListener {
            startActivity(Intent(this, TutorialActivity::class.java)) // Ensure this activity exists
        }
        btnShare.setOnClickListener {
            shareTaskDetails()
        }
        btnAbout.setOnClickListener {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }

        val button: Button = findViewById(R.id.btnShowGraph)
        button.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }
    }


    private fun showDatePicker(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, listener, year, month, day).show()
    }

    private fun showTimePicker(listener: TimePickerDialog.OnTimeSetListener) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(this, listener, hour, minute, true).show()
    }

    private val startDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        startDate = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        btnStartDate.text = dateFormat.format(startDate!!)
    }

    private val endDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        endDate = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        btnEndDate.text = dateFormat.format(endDate!!)
    }

    private val startTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val calendar = Calendar.getInstance()
        calendar.time = startDate ?: Date()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        startTime = calendar.time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        btnStartTime.text = timeFormat.format(startTime!!)
    }

    private val endTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val calendar = Calendar.getInstance()
        calendar.time = endDate ?: Date()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        endTime = calendar.time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        btnEndTime.text = timeFormat.format(endTime!!)
    }

    private fun saveTask() {
        val selectedItem = spinner.selectedItem.toString()
        val taskName = edName.text.toString()
        val taskDesc = edDesc.text.toString()
        val minGoal = edMinGoal.text.toString()
        val maxGoal = edMaxGoal.text.toString()

        if (taskName.isEmpty()) {
            edName.error = "Please enter a name for the task"
            return
        }

        if (taskDesc.isEmpty()) {
            edDesc.error = "Please enter a description for the task"
            return
        }
        if (minGoal.isEmpty()) {
            edMinGoal.error = "Please enter a minimum goal"
            return
        }
        if (maxGoal.isEmpty()) {
            edMaxGoal.error = "Please enter a maximum goal"
            return
        }
        if (startDate == null) {
            Toast.makeText(this, "Please set a start date", Toast.LENGTH_SHORT).show()
            return
        }
        if (endDate == null) {
            Toast.makeText(this, "Please set an end date", Toast.LENGTH_SHORT).show()
            return
        }
        if (startTime == null) {
            Toast.makeText(this, "Please set a start time", Toast.LENGTH_SHORT).show()
            return
        }
        if (endTime == null) {
            Toast.makeText(this, "Please set an end time", Toast.LENGTH_SHORT).show()
            return
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startDateString = dateFormat.format(startDate!!)
        val endDateString = dateFormat.format(endDate!!)
        val startTimeString = timeFormat.format(startTime!!)
        val endTimeString = timeFormat.format(endTime!!)

        // Calculate total time
        val totalTimeMillis = endDate!!.time - startDate!!.time + (endTime!!.time - startTime!!.time)
        val totalMinutes = totalTimeMillis / (1000 * 60)
        val totalHours = totalMinutes / 60
        val minutesRemaining = totalMinutes % 60
        val totalTimeString = String.format(Locale.getDefault(), "%02d:%02d", totalHours, minutesRemaining)

        saveToFirebase(selectedItem, taskName, taskDesc, minGoal, maxGoal, startDateString, endDateString, startTimeString, endTimeString, totalTimeString)
    }

    private fun saveToFirebase(selectedItem: String, taskName: String, taskDesc: String, minGoal: String, maxGoal: String, startDateString: String, endDateString: String, startTimeString: String, endTimeString: String, totalTimeString: String) {
        val key = database.child("items").push().key
        key?.let {
            val task = TaskModel(taskName, taskDesc, selectedItem, startDateString, endDateString, startTimeString, endTimeString, totalTimeString, minGoal, maxGoal)
            database.child("items").child(it).setValue(task).addOnSuccessListener {
                Toast.makeText(this, "Data added to firebase!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Error saving data: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareTaskDetails() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Sharing the details of the task: Name: ${edName.text},\n Description: ${edDesc.text},\n Start Date: ${btnStartDate.text},\n Start Time: ${btnStartTime.text},\n End Date: ${btnEndDate.text},\n End Time: ${btnEndTime.text},\n Minimum Goal: ${edMinGoal.text},\n Maximum Goal: ${edMaxGoal.text}\n")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share Task Via"))
    }

    private fun fetchAndDisplay() {
        database.child("items").get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val records = ArrayList<String>()
                dataSnapshot.children.forEach { snapshot ->
                    val task = snapshot.getValue(TaskModel::class.java)
                    task?.let {
                        records.add("Name: ${it.taskName},\n" +
                                "Description: ${it.taskDesc},\n" +
                                "Category: ${it.selectedItem},\n" +
                                "Start Date: ${it.startDateString},\n " +
                                "Start Time: ${it.startTimeString},\n " +
                                "End Date: ${it.endDateString},\n" +
                                "End Time: ${it.endTimeString},\n " +
                                "Total time: ${it.totalTimeString},\n" +
                                "Minimum Goal: ${it.minGoal},\n" +
                                "Maximum Goal: ${it.maxGoal}\n"
                        )
                    }
                }
                displayRecordsDialog(records)
            } else {
                Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayRecordsDialog(records: ArrayList<String>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Task Records")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, records)
        builder.setAdapter(arrayAdapter, null)
        builder.setPositiveButton("OK", null)
        builder.show()
    }
}

// Data class
data class TaskModel(
    var taskName: String? = null,
    var taskDesc: String? = null,
    var selectedItem: String? = null,
    var startDateString: String? = null,
    var endDateString: String? = null,
    var startTimeString: String? = null,
    var endTimeString: String? = null,
    var totalTimeString: String? = null,
    var minGoal: String? = "0", // Minimum daily goal
    var maxGoal: String? = "0" // Maximum daily goal
)
