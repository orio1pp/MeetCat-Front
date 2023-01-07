package com.pes.meetcatui.network

import kotlinx.serialization.Serializable

@Serializable
data class RoleData(
    var id: Long?,
    var name: String? = null
)
