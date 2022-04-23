package com.example.weecompanyjctest.repository

import android.util.Log
import com.example.weecompanyjctest.model.Country
import com.example.weecompanyjctest.provider.CountryProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject


interface ICountryRepository {
    suspend fun getContries(): List<Country>
}

class CountryRepository @Inject constructor(
    private val countryProvider: CountryProvider
) : ICountryRepository{

    private var contries: List<Country> = emptyList()

    override suspend fun getContries(): List<Country> {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("cadena", "")
            val jsonObjectString = jsonObject.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            val apiResponse = countryProvider.getCountries(requestBody).body()
            contries = apiResponse?.dsRespuesta?.countries ?: emptyList()
        }
        catch (e: Exception){
            e.message?.let { Log.e("CountryList", it) }
        }
        return contries
    }
}