package com.alpha.eventplanner

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val dateTime: String,
    val location: String,
    val isSynced: Boolean = false // Flag to check if it's synced with JSONBin
)

// Conversion from EventEntity to Event
fun EventEntity.toEvent(): Event {
    return Event(
        id = this.id.toString(),
        title = this.title,
        dateTime = this.dateTime,
        location = this.location
    )
}

// Conversion from Event to EventEntity
fun Event.toEventEntity(isSynced: Boolean = false): EventEntity {
    return EventEntity(
        id = if (this.id == "0") 0 else this.id.toInt(), // If id is "0" for new events
        title = this.title,
        dateTime = this.dateTime,
        location = this.location,
        isSynced = isSynced
    )
}
