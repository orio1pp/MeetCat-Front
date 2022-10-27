package com.pes.meetcatui.feature_event

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeFormatter {
    fun strLocalDateTime_to_Date(startDate: String, endDate: String): String? {

        val startDate = LocalDateTime.parse(startDate)
        val endDate = LocalDateTime.parse(endDate)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        if (startDate.toLocalDate().isEqual(endDate.toLocalDate()))
            return startDate.toLocalDate().format(formatter)

        return null
    }

    fun strLocalDateTime_to_Time(
        startDate: String,
        endDate: String,
        fromText: String = "From",
        toText: String = "to",
        atTextStart: String = "at ",
        atTextEnd: String = atTextStart
    ): String {

        val startDate = LocalDateTime.parse(startDate)
        val endDate = LocalDateTime.parse(endDate)

        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

        val startTime = startDate.toLocalTime().format(formatterTime)
        val endTime = endDate.toLocalTime().format(formatterTime)

        if (startDate.toLocalDate().isEqual(endDate.toLocalDate()))
            return "$fromText $startTime $toText $endTime"

        val formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val startDateFormatted = startDate.toLocalDate().format(formatterDate)
        val endDateFormatted = endDate.toLocalDate().format(formatterDate)

        return "$fromText $startDateFormatted $atTextStart$startTime $toText $endDateFormatted $atTextEnd$endTime"
    }

    fun strLocalDateTime_to_DateTime(date: String): String {
        val startDate = LocalDateTime.parse(date)
        val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yy")
        val startDateFormatted = startDate.toLocalDate().format(formatterDate)

        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val startTime = startDate.toLocalTime().format(formatterTime)

        return "$startDateFormatted $startTime"
    }
}