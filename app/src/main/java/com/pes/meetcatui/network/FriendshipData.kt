package com.pes.meetcatui.network

import kotlinx.serialization.Serializable

@Serializable
data class FriendshipData(
    var friendId: String,
    var ownerId: String
)
