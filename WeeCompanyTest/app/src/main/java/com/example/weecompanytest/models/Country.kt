package com.example.weecompanytest.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Country(
    @SerializedName("idPais")
    val id: UUID,
    @SerializedName("Pais")
    val country: String,
    var isSelect: Boolean = false
)