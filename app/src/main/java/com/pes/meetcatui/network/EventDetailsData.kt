package com.pes.meetcatui.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDetailsData(
    @SerialName("id")
    val eventId: Long,
    @SerialName("title")
    val name: String,
    val subtitle: String?,
    val username: String?,
    val description: String?,
    @SerialName("initDate")
    val startDate: String,
    val endDate: String?,
    val link: String?,
    val placeName: String?,
    val location: String?,
    val address: String?,
    val attendeesCount: Int,
)