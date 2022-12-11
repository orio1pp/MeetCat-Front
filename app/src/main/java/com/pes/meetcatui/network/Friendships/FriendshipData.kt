package com.pes.meetcatui.network.Friendships

import kotlinx.serialization.Serializable

@Serializable
data class FriendshipData(
    var id: Long,
    var friendId: String,
    var ownerId: String
)
