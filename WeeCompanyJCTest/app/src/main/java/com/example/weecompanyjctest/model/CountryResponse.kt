package com.example.weecompanyjctest.model

import com.google.gson.annotations.SerializedName

data class GetAllResponse(
    @SerializedName("IsOk")
    val success: Boolean,
    @SerializedName("dsRespuesta")
    val dsRespuesta: CountryResponse
)

data class CountryResponse(
    @SerializedName("Paises")
    val countries: MutableList<Country> = mutableListOf()
)