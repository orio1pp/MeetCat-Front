package com.pes.meetcatui.feature_user.domain

class User(
    private var id: Long,
    private var username: String,
    private var password: String,
    private var friends: List<User>
) {

}