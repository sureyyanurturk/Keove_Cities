package com.example.keovecities.models

import com.google.gson.annotations.SerializedName

data class UserLoginModel(

    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String

)
