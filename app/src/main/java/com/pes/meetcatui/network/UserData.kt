package com.pes.meetcatui.network

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserData(
    var id: Long?,
    var username: String,
    var password: String,
    var roles: MutableCollection<RoleData> = mutableListOf<RoleData>(),
    var about: String?,
    @Contextual
    var lastUpdate: LocalDateTime? = null,
    @Contextual
    var createdDate: LocalDateTime? = null
)
