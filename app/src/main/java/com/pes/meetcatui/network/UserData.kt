package com.pes.meetcatui.network

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    var id: Long?,
    var username: String,
    var password: String,
    var roles: List<RoleData>,
    var about: String?,
    var lastUpdate: String? = null,
    var createdDate: String? = null
)
