package com.example.kimali.Login

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User_DB(
    var user_pw: String? = "",

    var nameText: String? = ""
)