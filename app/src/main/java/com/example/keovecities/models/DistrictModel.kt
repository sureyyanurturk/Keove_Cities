package com.example.keovecities.models

import com.google.gson.annotations.SerializedName

data class DistrictModel(
    @SerializedName("cityId")
    val cityId: Int,
    @SerializedName("name")
    val name: String


)
