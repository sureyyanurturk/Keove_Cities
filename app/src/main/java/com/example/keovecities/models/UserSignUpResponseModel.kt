package com.example.keovecities.models

import com.google.gson.annotations.SerializedName

data class UserSignUpResponseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("isGuest")
    val isGuest: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
