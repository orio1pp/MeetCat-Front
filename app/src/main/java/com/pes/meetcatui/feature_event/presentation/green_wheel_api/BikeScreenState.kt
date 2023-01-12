package com.pes.meetcatui.feature_event.presentation.green_wheel_api

import com.pes.meetcatui.feature_event.domain.green_wheel_api.Bike
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Charger

data class BikeScreenState(
    val data: MutableList<Bike>? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isDetailsSelected: Boolean = false,
    val bikeDetailsSelected: Bike? = null,

    )
