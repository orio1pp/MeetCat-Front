package com.pes.meetcatui.feature_chat.domain

import com.pes.meetcatui.network.Friendships.FriendshipData

data class UserFriendship(
    val friendship: FriendshipData,
    val hasChat: Boolean
)
