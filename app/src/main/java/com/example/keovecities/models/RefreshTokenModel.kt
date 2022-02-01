package com.example.keovecities.models

import com.google.gson.annotations.SerializedName

data class RefreshTokenModel(
    @SerializedName("refreshToken")
    val refreshToken: String

)
