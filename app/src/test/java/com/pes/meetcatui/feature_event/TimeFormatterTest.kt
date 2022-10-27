package com.pes.meetcatui.feature_event

import org.junit.Assert
import org.junit.Test

class TimeFormatterTest {

    @Test
    fun TestStrLocalDateToTime_SameDay_isCorrect() {
        Assert.assertEquals(
            "25-10-2022",
            TimeFormatter().strLocalDateTime_to_Date("2022-10-25T10:00:00", "2022-10-25T23:59:59")
        )
    }

    @Test
    fun TestStrLocalDateToDate_SameDay_isCorrect() {
        Assert.assertEquals(
            "From 10:00 to 23:59",
            TimeFormatter().strLocalDateTime_to_Time("2022-10-25T10:00:00.292030", "2022-10-25T23:59:59")
        )
    }

    @Test
    fun TestStrLocalDateToTime_DifferentDay_isCorrect() {
        Assert.assertEquals(
            null,
            TimeFormatter().strLocalDateTime_to_Date("2022-10-15T10:00:00", "2022-10-25T23:59:59")
        )
    }

    @Test
    fun TestStrLocalDateToDate_DifferentDay_isCorrect() {
        Assert.assertEquals(
            "From 15-10-2022 at 10:00 to 25-10-2022 at 23:59",
            TimeFormatter().strLocalDateTime_to_Time("2022-10-15T10:00:00.29203", "2022-10-25T23:59:59")
        )
    }
}