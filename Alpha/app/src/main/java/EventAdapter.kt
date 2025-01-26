package com.alpha.eventplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private val events: MutableList<Event>,
    private val onEditClick: (Event) -> Unit,
    private val onDeleteClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // Track the last position to apply animation only once per item
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleTextView.text = event.title
        holder.dateTimeTextView.text = event.dateTime
        holder.locationTextView.text = event.location

        // Set click listeners for Edit and Delete buttons
        holder.editButton.setOnClickListener { onEditClick(event) }
        holder.deleteButton.setOnClickListener { onDeleteClick(event) }

        // Apply slide-in animation
        setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int = events.size

    // Add methods to update the events list
    fun updateEvents(newEvents: List<Event>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged() // Notify the adapter that data has changed
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.eventTitle)
        val dateTimeTextView: TextView = itemView.findViewById(R.id.eventDateTime)
        val locationTextView: TextView = itemView.findViewById(R.id.eventLocation)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    // Slide-in animation method
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, we apply the animation
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    // Clear the animation when the view is detached
    override fun onViewDetachedFromWindow(holder: EventViewHolder) {
        holder.itemView.clearAnimation()
    }
}
