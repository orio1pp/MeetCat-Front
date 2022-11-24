package com.pes.meetcatui.feature_event.domain

import kotlinx.serialization.Serializable

@Serializable
data class EventPage(
    val events: List<Event>,
    val pageNum: Int
)
