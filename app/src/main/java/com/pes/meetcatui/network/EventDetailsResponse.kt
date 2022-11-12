package com.pes.meetcatui.network

import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EventDetailsData(
    @SerialName("id")
    val eventId: Int,
    @SerialName("titol")
    val name: String,
    @SerialName("subtitol")
    val subtitle: String,
    @SerialName("descripcio")
    val description: String,
    @SerialName("dataInici")
    val startDate: String,
    @SerialName("dataFi")
    val endDate: String,
    @SerialName("nomLloc")
    val locationName: String,
    val address: String,
    val link: String
)