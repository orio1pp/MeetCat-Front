package com.pes.meetcatui.network

import com.pes.meetcatui.feature_event.domain.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EventDetailsData(
    @SerialName("id")
    val eventId: Long,
    @SerialName("title")
    val name: String,
    val subtitle: String?,
    val description: String?,
    @SerialName("initDate")
    val startDate: String,
    val endDate: String?,
    val link: String?,
    val placeName: String?,
    val location: String?,
    val address: String,
)