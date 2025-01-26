package com.alpha.eventplanner

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import android.os.Build


class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private val PREFS_NAME = "event_prefs"
    private val EVENTS_KEY = "events"
    private val eventsList = mutableListOf<Event>()
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        calendarView = view.findViewById(R.id.calendarView)
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadEvents()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            val eventsForDate = eventsList.filter {
                dateFormatter.format(dateFormatter.parse(it.dateTime.split(" ")[0])) == selectedDate
            }

            if (eventsForDate.isNotEmpty()) {
                showEventDetails(eventsForDate)
                triggerNotificationForEvents(eventsForDate, selectedDate)
            } else {
                showNoEventMessage(selectedDate)
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        loadEvents()
    }

    private fun loadEvents() {
        val json = sharedPreferences.getString(EVENTS_KEY, null)
        json?.let {
            val type = object : TypeToken<List<Event>>() {}.type
            eventsList.clear()
            eventsList.addAll(gson.fromJson(it, type))
        }
    }

    private fun showEventDetails(events: List<Event>) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.event_details_bottom_sheet, null)
        val eventTitleTextView = bottomSheetView.findViewById<TextView>(R.id.eventTitleText)
        val eventDateTimeTextView = bottomSheetView.findViewById<TextView>(R.id.eventDateTimeText)
        val eventLocationTextView = bottomSheetView.findViewById<TextView>(R.id.eventLocationText)

        eventTitleTextView.text = events[0].title
        eventDateTimeTextView.text = events[0].dateTime
        eventLocationTextView.text = events[0].location

        if (events.size > 1) {
            events.drop(1).joinToString("\n") { "${it.title} - ${it.dateTime} - ${it.location}" }
                .let { eventTitleTextView.append("\nOther events on this date:\n$it") }
        }

        bottomSheetView.findViewById<View>(R.id.closeButton).setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
    // Show Events on Calendar
    private fun showNoEventMessage(date: String) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.event_details_bottom_sheet, null)
        bottomSheetView.findViewById<TextView>(R.id.eventTitleText).text = "No Event"
        bottomSheetView.findViewById<TextView>(R.id.eventDateTimeText).text = "No event found on $date"
        bottomSheetView.findViewById<View>(R.id.closeButton).setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
    // Notification Channel
    private fun triggerNotificationForEvents(events: List<Event>, date: String) {
        val notificationManager = ContextCompat.getSystemService(requireContext(), NotificationManager::class.java) as NotificationManager
        val notificationChannelId = "event_notification_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId, "Event Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(requireContext(), notificationChannelId)
            .setSmallIcon(R.drawable.alpine)
            .setContentTitle("Events on $date")
            .setContentText(events.joinToString("\n") { "${it.title} at ${it.dateTime}" })
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(Random().nextInt(), notificationBuilder.build())
    }
}
