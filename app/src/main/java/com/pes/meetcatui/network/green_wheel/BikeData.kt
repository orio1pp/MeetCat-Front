package com.pes.meetcatui.network.green_wheel

@kotlinx.serialization.Serializable
class BikeData (
    var id: Long?,
    var localization: LocalizationData?,
    var bike_type: BikeTypeData?
)
