package com.buildbyte.npschamps

//this data class is used when calling the data from firestore to ensure it's all valid
data class EventData(
    val id: String,
    val title: String,
    val date: java.sql.Timestamp,
    val location: String,
    val description: String,
    val imageResId: String
)

data class InitiativeData(
    val title: String,
    val description: String,
    val imageResId: String
)

