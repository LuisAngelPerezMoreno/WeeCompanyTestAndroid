package com.example.weecompanyjctest.provider

import com.example.weecompanyjctest.model.GetAllResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CountryProvider {
    @POST(value = "Utilidades/Pais_GetPais")
    suspend fun getCountries(@Body requestBody: RequestBody): Response<GetAllResponse>
}