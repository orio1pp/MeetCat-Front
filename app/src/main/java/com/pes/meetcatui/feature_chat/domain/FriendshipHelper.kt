package com.pes.meetcatui.feature_chat.domain

data class FriendshipHelper(
    val user: com.pes.meetcatui.network.UserData?,
    var isFriend: Boolean
)
