package com.pes.meetcatui.feature_user.domain



@kotlinx.serialization.Serializable
class User {
    var uid: String? = null
    var name: String? = null
    var email: String? = null

    var isAuthenticated = false

    var isNew = false
    var isCreated = false

    fun User() {}

    fun User(uid: String?, name: String?, email: String?) {
        this.uid = uid
        this.name = name
        this.email = email
    }
}