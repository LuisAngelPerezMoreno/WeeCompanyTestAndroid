package com.example.weecompanytest.services

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import kotlin.jvm.Throws


const val BASE_URL = "https://weepatient.com/API/api/"

class Api {

    fun SendRequest(
        context: Context,
        Method: Int,
        url: String,
        jsonRequest: JSONObject? = null,
        response: Listener,
        ErrorListener: ErrorListener
    ) {
        val request = object : JsonObjectRequest(Method, "$BASE_URL$url", jsonRequest,
            Response.Listener {
                response.onResponse(it)
            },
            Response.ErrorListener {
                when(it) {
                    is AuthFailureError -> {
                        val body = String(it.networkResponse.data, charset("UTF-8"))
                        ErrorListener.onErrorResponse(it, JSONObject(body))
                    }
                    else -> {
                        ErrorListener.onErrorResponse(it)
                    }

                }
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Accept"] = "application/json"
                params["Content-Type"] = "application/json"
                return params
            }
        }
        request.retryPolicy = CustomRetryPolicy(30000, 2, 1f)
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }
}

interface Listener{
    fun onResponse(response: JSONObject)
}

interface ErrorListener {
    fun onErrorResponse(error: VolleyError, response: JSONObject? = null)
}