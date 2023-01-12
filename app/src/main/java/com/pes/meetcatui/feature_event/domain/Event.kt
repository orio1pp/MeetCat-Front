package com.pes.meetcatui.feature_event.domain

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val eventId: Long,
    val name: String,
    val subtitle: String?,
    val username: String?,
    val description: String?,
    val startDate: String,
    val endDate: String?,
    val location: String?,
    val placeName: String?,
    val address: String?,
    val link: String?,
    val attendeesCount: Int,
)