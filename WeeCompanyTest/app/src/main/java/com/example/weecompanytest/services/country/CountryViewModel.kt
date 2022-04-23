package com.example.weecompanytest.services.country

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.example.weecompanytest.models.Country
import com.example.weecompanytest.models.CountryRequest
import com.example.weecompanytest.models.GetAllResponse
import com.example.weecompanytest.services.Api
import com.example.weecompanytest.services.ErrorListener
import com.example.weecompanytest.services.Listener
import com.google.gson.Gson
import org.json.JSONObject
import java.io.Console

enum class CountryViewModelType{
    Listado
}

interface ICountryViewModel{
    fun response(type: CountryViewModelType, success: Boolean, message: String? = null)
}

class CountryViewModel(val context: Context, val callback: ICountryViewModel) {
    private val api = Api()
    private val gson = Gson()
    var listado: MutableList<Country> = mutableListOf()

    fun getCountrys(){
        val request = CountryRequest()
        api.SendRequest(context, Request.Method.POST, "Utilidades/Pais_GetPais", JSONObject(gson.toJson(request)), object: Listener {
            override fun onResponse(response: JSONObject) {
                val respuesta = gson.fromJson(response.toString(), GetAllResponse::class.java)
                listado = respuesta.dsRespuesta.countries
                callback.response(CountryViewModelType.Listado, true, null)
            }
        }, object: ErrorListener {
            override fun onErrorResponse(error: VolleyError, response: JSONObject?) {
                callback.response(CountryViewModelType.Listado, false, "Ocurrio un error al obtener listado")
            }
        })
    }
}