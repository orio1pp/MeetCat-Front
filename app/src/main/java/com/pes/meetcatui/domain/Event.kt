package com.pes.meetcatui.domain

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val eventId: Int,
    val name: String,
    val description: String,
    val date: String,
)