package com.pes.meetcatui.feature_event.domain

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val eventId: Int,
    val name: String,
    val subtitle: String?,
    val description: String,
    val startDate: String?,
    val endDate: String?,
    val locationName: String?,
    val address: String,
    val link: String?,
)