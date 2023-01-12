package com.pes.meetcatui.feature_event.presentation.green_wheel_api

import com.pes.meetcatui.feature_event.domain.green_wheel_api.Bike
import com.pes.meetcatui.feature_event.domain.green_wheel_api.Charger

data class ChargerScreenState(
    val data: MutableList<Charger>? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isDetailsSelected: Boolean = false,
    val chargerDetailsSelected: Charger? = null,

    )
