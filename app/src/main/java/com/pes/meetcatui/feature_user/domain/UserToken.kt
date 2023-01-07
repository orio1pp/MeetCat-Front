package com.pes.meetcatui.feature_user.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserToken(
    var access_token : String,
    var refresh_token : String
)
