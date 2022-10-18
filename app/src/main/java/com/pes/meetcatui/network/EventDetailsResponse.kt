package com.pes.meetcatui.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EventDetailsData(
    @SerialName("id")
    val eventId: Int,
    @SerialName("titol")
    val eventName: String,
    @SerialName("descripcio")
    val eventDescription: String,
    @SerialName("data")
    val date: String,
    val lastUpdate: String,
    val createdDate: String
)