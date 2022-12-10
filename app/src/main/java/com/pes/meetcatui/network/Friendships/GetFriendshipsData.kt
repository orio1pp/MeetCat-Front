package com.pes.meetcatui.network.Friendships

import kotlinx.serialization.Serializable

@Serializable
data class GetFriendshipsData(
    var username: String,
    val page: Int,
    val size: Int
)
