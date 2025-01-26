package com.alpha.eventplanner

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alpha.eventplanner.BinData
import com.alpha.eventplanner.Event
import com.alpha.eventplanner.EventAdapter
import com.alpha.eventplanner.R
import com.alpha.eventplanner.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import android.os.Build

class HomeFragment : Fragment() {

    private lateinit var eventTitleInput: TextInputEditText
    private lateinit var dateTimeInput: TextInputEditText
    private lateinit var locationInput: TextInputEditText
    private lateinit var addEventButton: Button
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var searchView: SearchView
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private val PREFS_NAME = "event_prefs"
    private val EVENTS_KEY = "events"
    private val eventsList = mutableListOf<Event>()
    private val filteredEventsList = mutableListOf<Event>()

    private lateinit var connectivityReceiver: ConnectivityReceiver
    private lateinit var eventDao: EventDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("HomeFragment", "onCreateView called")

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize UI elements
        eventTitleInput = view.findViewById(R.id.eventTitleInput)
        dateTimeInput = view.findViewById(R.id.dateTimeInput)
        locationInput = view.findViewById(R.id.locationInput)
        addEventButton = view.findViewById(R.id.addEventButton)
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView)
        searchView = view.findViewById(R.id.searchView)

        // Set up SearchView click listener for the entire layout
        val searchLayout = view.findViewById<LinearLayout>(R.id.searchLayout)
        searchLayout.setOnClickListener {
            searchView.isIconified = false // Make the SearchView focusable
        }

        // Set touch target size for Add Event Button
        addEventButton.minHeight = 48
        addEventButton.minWidth = 48
        addEventButton.setPadding(16, 16, 16, 16)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        Log.i("HomeFragment", "SharedPreferences initialized")

        // Initialize EventDao (assuming you have set up Room)
        val db = EventDatabase.getDatabase(requireContext())
        eventDao = db.eventDao()

        // Set up RecyclerView
        eventAdapter = EventAdapter(
            filteredEventsList,
            onEditClick = { event -> showEditDialog(event) },
            onDeleteClick = { event -> deleteEvent(event) }
        )
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.adapter = eventAdapter
        Log.d("HomeFragment", "RecyclerView and Adapter set up")

        // Load events from SharedPreferences
        loadEvents()

        // Fetch all events when the Fragment is created
        fetchEvents()

        // Set an OnClickListener for the date and time input field to open a DatePickerDialog followed by TimePickerDialog
        dateTimeInput.setOnClickListener {
            showDatePickerAndTimePicker()
        }

        // Set onClickListener for adding a new event
        addEventButton.setOnClickListener {
            val title = eventTitleInput.text.toString()
            val dateTime = dateTimeInput.text.toString()
            val location = locationInput.text.toString()

            Log.d("HomeFragment", "Add Event Button Clicked: title=$title, dateTime=$dateTime, location=$location")

            if (title.isNotEmpty() && dateTime.isNotEmpty() && location.isNotEmpty()) {
                val newEvent = Event(id = "0", title, dateTime, location)
                createEvent(newEvent)
                Toast.makeText(requireContext(), "Event added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                Log.w("HomeFragment", "Empty fields in Add Event form")
            }
        }

        // Set up SearchView listener
        setupSearchView()

        // Register the connectivity receiver and provide a callback for network changes
        connectivityReceiver = ConnectivityReceiver {
            if (!isNetworkAvailable()) {
                Toast.makeText(requireContext(), "Network restored. Syncing data...", Toast.LENGTH_SHORT).show()
            }
            syncData() // Call the syncData() method when network is back
        }

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, filter)

        return view
    }

    // Check if network is available
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    private fun showDatePickerAndTimePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Show DatePickerDialog
        val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)

            // Show TimePickerDialog after selecting the date
            val timePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                dateTimeInput.setText("$selectedDate $selectedTime") // Set the selected date and time
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

            timePicker.show()

        }, year, month, day)

        datePicker.show()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("HomeFragment", "SearchView submit: query=$query")
                filterEvents(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("HomeFragment", "SearchView text change: newText=$newText")
                filterEvents(newText)
                return true
            }
        })
    }

    private fun filterEvents(query: String?) {
        Log.i("HomeFragment", "Filtering events for query=$query")
        filteredEventsList.clear()
        if (query.isNullOrEmpty()) {
            filteredEventsList.addAll(eventsList)
        } else {
            val filtered = eventsList.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.dateTime.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true)
            }
            filteredEventsList.addAll(filtered)
        }
        eventAdapter.notifyDataSetChanged()
        Log.d("HomeFragment", "Event filtering completed, filtered list size=${filteredEventsList.size}")
    }

    private fun loadEvents() {
        val json = sharedPreferences.getString(EVENTS_KEY, null)
        if (json != null) {
            val type = object : TypeToken<List<Event>>() {}.type
            val savedEvents: List<Event> = gson.fromJson(json, type)
            eventsList.clear()
            eventsList.addAll(savedEvents)
            filteredEventsList.clear()
            filteredEventsList.addAll(savedEvents)
            eventAdapter.notifyDataSetChanged()
            Log.i("HomeFragment", "Events loaded from SharedPreferences, event list size=${eventsList.size}")
        } else {
            Log.w("HomeFragment", "No events found in SharedPreferences")
        }
    }

    private fun saveEvents() {
        val json = gson.toJson(eventsList)
        sharedPreferences.edit().putString(EVENTS_KEY, json).apply()
        Log.i("HomeFragment", "Events saved to SharedPreferences")
        Toast.makeText(requireContext(), "Events saved!", Toast.LENGTH_SHORT).show()
    }

    private fun fetchEvents() {
        RetrofitInstance.api.getEvents().enqueue(object : Callback<BinData> {
            override fun onResponse(call: Call<BinData>, response: Response<BinData>) {
                if (response.isSuccessful) {
                    val events = response.body()?.events
                    if (events != null) {
                        eventsList.clear()
                        eventsList.addAll(events)
                        filteredEventsList.clear()
                        filteredEventsList.addAll(events)
                        eventAdapter.notifyDataSetChanged()
                        Log.i("HomeFragment", "Events fetched from API, event list size=${eventsList.size}")
                        Toast.makeText(requireContext(), "Events fetched successfully!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("HomeFragment", "Failed to fetch events, response unsuccessful")
                }
            }

            override fun onFailure(call: Call<BinData>, t: Throwable) {
                Log.e("HomeFragment", "Error fetching events: ${t.message}")
                Toast.makeText(requireContext(), "Failed to fetch events: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createEvent(event: Event) {
        eventsList.add(event) // Add to local list
        filteredEventsList.add(event) // Ensure it shows up immediately
        eventAdapter.notifyDataSetChanged() // Notify the adapter

        saveEvents() // Save to SharedPreferences

        triggerNotificationForNewEvent(event)

        // Show appropriate toast based on network availability
        if (!isNetworkAvailable()) {
            Toast.makeText(requireContext(), "Event will be synced when back online", Toast.LENGTH_SHORT).show()
        } else {
            updateEvents() // Sync with server if online
            Toast.makeText(requireContext(), "Event added successfully!", Toast.LENGTH_SHORT).show()
        }

        Log.d("HomeFragment", "Event created: $event")
    }

    private fun triggerNotificationForNewEvent(event: Event) {
        val notificationManager = ContextCompat.getSystemService(requireContext(), NotificationManager::class.java) as NotificationManager
        val channelId = "event_notifications"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Event Notifications"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.alpine)
            .setContentTitle("New Event Added")
            .setContentText("Title: ${event.title}, Date & Time: ${event.dateTime}, Location: ${event.location}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(Random().nextInt(), notificationBuilder.build())
    }
    private fun updateEvents() {
        val binData = BinData(eventsList)
        RetrofitInstance.api.updateEvents(binData).enqueue(object : Callback<BinData> {
            override fun onResponse(call: Call<BinData>, response: Response<BinData>) {
                if (response.isSuccessful) {
                    Log.i("HomeFragment", "Events updated in JSONBin")
                    fetchEvents() // Automatically refresh the list after update
                } else {
                    Log.e("HomeFragment", "Failed to update events in JSONBin")
                    Toast.makeText(requireContext(), "Failed to update events", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BinData>, t: Throwable) {
                Log.e("HomeFragment", "Error updating events: ${t.message}")
                Toast.makeText(requireContext(), "Error updating events: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showEditDialog(event: Event) {
        val dialog = EditEventDialogFragment(event) { updatedEvent ->
            updateEvent(updatedEvent)
        }
        dialog.show(parentFragmentManager, "EditEventDialog")
        Log.d("HomeFragment", "Edit dialog shown for event: $event")
    }

    private fun updateEvent(event: Event) {
        val index = eventsList.indexOfFirst { it.id == event.id }
        if (index != -1) {
            eventsList[index] = event
            filteredEventsList[index] = event // Update both lists
            eventAdapter.notifyDataSetChanged() // Notify the adapter
            saveEvents() // Save to SharedPreferences
            updateEvents() // Automatically refresh the list after update
            Log.d("HomeFragment", "Event updated: $event")
        }
    }

    private fun deleteEvent(event: Event) {
        eventsList.remove(event)
        filteredEventsList.remove(event)
        eventAdapter.notifyDataSetChanged()
        saveEvents() // Save to SharedPreferences
        updateEvents() // Automatically refresh the list after delete
        Log.d("HomeFragment", "Event deleted: $event")
        Toast.makeText(requireContext(), "Event deleted successfully!", Toast.LENGTH_SHORT).show()
    }

    // Sync offline data when connectivity is restored
    fun syncData() {
        if (!isNetworkAvailable()) return

        lifecycleScope.launch {
            // Get unsynced events from RoomDB
            val unsyncedEvents = eventDao.getUnsyncedEvents()

            if (unsyncedEvents.isNotEmpty()) {
                Toast.makeText(requireContext(), "Syncing data with server...", Toast.LENGTH_SHORT).show() // Notify about sync starting

                for (eventEntity in unsyncedEvents) {
                    val event = eventEntity.toEvent() // Convert EventEntity to Event
                    val binData = BinData(listOf(event))
                    RetrofitInstance.api.updateEvents(binData).enqueue(object : Callback<BinData> {
                        override fun onResponse(call: Call<BinData>, response: Response<BinData>) {
                            if (response.isSuccessful) {
                                lifecycleScope.launch {
                                    eventDao.markAsSynced(eventEntity.id) // Mark as synced
                                    Log.d("HomeFragment", "Event synced with JSONBin: ${event.title}")
                                    // Refresh the RecyclerView
                                    eventsList.add(event)
                                    filteredEventsList.add(event)
                                    eventAdapter.notifyDataSetChanged()
                                }
                            } else {
                                Log.e("HomeFragment", "Failed to sync event with JSONBin")
                            }
                        }

                        override fun onFailure(call: Call<BinData>, t: Throwable) {
                            Log.e("HomeFragment", "Error syncing event: ${t.message}")
                        }
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the connectivity
        requireContext().unregisterReceiver(connectivityReceiver)
    }
}
