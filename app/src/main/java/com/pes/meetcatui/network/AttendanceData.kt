package com.pes.meetcatui.network

import kotlinx.serialization.Serializable

@Serializable
data class AttendanceData(
    val userId: Long,
    val eventId: Long,
)
