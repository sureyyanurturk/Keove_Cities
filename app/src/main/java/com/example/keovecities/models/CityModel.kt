package com.example.keovecities.models

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String


)
