package com.pes.meetcatui.feature_event.domain

import kotlinx.serialization.Serializable

@Serializable
class Attendance(
    val userId: Long,
    val eventId: Long,
)