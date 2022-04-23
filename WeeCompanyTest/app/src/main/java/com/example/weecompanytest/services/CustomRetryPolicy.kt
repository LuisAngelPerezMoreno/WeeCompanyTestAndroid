package com.example.weecompanytest.services

import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError


class CustomRetryPolicy(initialTimeOutMs: Int,maxNumRetries: Int, backoffMultiplier: Float):
    RetryPolicy {
    private val SC_UNAUTHORIZED = 401
    private val TAG = "CustomRetryPolicy"

    private var defaultRetryPolicy: DefaultRetryPolicy = DefaultRetryPolicy(initialTimeOutMs,maxNumRetries,backoffMultiplier)

    override fun retry(error: VolleyError?) {
        if (error?.networkResponse?.statusCode == SC_UNAUTHORIZED) {
            Log.d(TAG,"not retry")
            throw error
        } else {
            defaultRetryPolicy.retry(error)
        }
    }

    override fun getCurrentRetryCount(): Int {
        return defaultRetryPolicy.currentRetryCount
    }

    override fun getCurrentTimeout(): Int {
        return defaultRetryPolicy.currentTimeout
    }

}