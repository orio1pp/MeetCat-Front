package com.pes.meetcatui.feature_chat.domain

import java.util.*

data class Message(
    var id: Long? = 0,
    var username: String? = "",
    var date: Date = Date(2022, 0, 2),
    var text: String = "",
) {
    /*
    override fun toString(): String {
        return "Message(id=$id, username='$username', date=$date, text='$text')"
    }*/

}