package com.example.appproto

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class GraphActivity : AppCompatActivity() {
    // Variables
    lateinit var edOne: EditText
    lateinit var edTwo: EditText
    lateinit var edThree: EditText
    lateinit var edFour: EditText
    lateinit var edSpin: EditText
    lateinit var spinner: Spinner
    lateinit var btnChart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        // Typecasts
        edOne = findViewById(R.id.edOne)
        edTwo = findViewById(R.id.edTwo)
        edThree = findViewById(R.id.edThree)
        edFour = findViewById(R.id.edFour)
        edSpin = findViewById(R.id.edSpinner)
        spinner = findViewById(R.id.spinner)
        btnChart = findViewById(R.id.btnChart)

        // Create array adapter --> Populate it -> drop down menu
        val spinnerAdapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item,
            mutableListOf() // create an empty mutable list of strings
        )
        // set the layout for the spinner
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // now set the spinner to the adapter
        spinner.adapter = spinnerAdapter

        // get the vales from the ed's
        btnChart.setOnClickListener {
            // Initialize a flag to track input validation
            var isValidInput = true

            // Helper function to validate and parse float
            fun validateAndParseFloat(input: EditText): Float? {
                return try {
                    input.text.toString().toFloat()
                } catch (e: NumberFormatException) {
                    input.error = "Please enter a valid number"
                    isValidInput = false
                    null
                }
            }

            // Validate each input
            val data1 = validateAndParseFloat(edOne)
            val data2 = validateAndParseFloat(edTwo)
            val data3 = validateAndParseFloat(edThree)
            val data4 = validateAndParseFloat(edFour)

            // Proceed only if all inputs are valid
            if (isValidInput) {
                val barEntries = arrayListOf<BarEntry>()
                data1?.let { barEntries.add(BarEntry(0f, it)) }
                data2?.let { barEntries.add(BarEntry(1f, it)) }
                data3?.let { barEntries.add(BarEntry(2f, it)) }
                data4?.let { barEntries.add(BarEntry(3f, it)) }

                val barDataSet = BarDataSet(barEntries, "Data Set")
                barDataSet.colors = listOf(Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE)
                val barData = BarData(barDataSet)

                val chart = findViewById<BarChart>(R.id.barChart)
                chart.data = barData
                chart.invalidate() // Refresh the chart
            }
        }
    }
}
