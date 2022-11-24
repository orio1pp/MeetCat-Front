package com.pes.meetcatui.network

import kotlinx.serialization.Serializable

@Serializable
data class EventsData(
    val events : List<EventDetailsData>,
    val page: Int?,
    val size: Int?
)
