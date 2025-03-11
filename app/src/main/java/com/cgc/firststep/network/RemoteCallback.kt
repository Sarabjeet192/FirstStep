package com.cgc.firststep.network

import android.text.TextUtils
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

/**
 * Generic class to handle web service response
 *
 * @author Sarabjeet Singh on 25/10/2023
 */

abstract class RemoteCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when (response.code()) {
            HttpsURLConnection.HTTP_OK, HttpsURLConnection.HTTP_CREATED, HttpsURLConnection.HTTP_ACCEPTED,
            HttpsURLConnection.HTTP_NOT_AUTHORITATIVE, HttpsURLConnection.HTTP_NO_CONTENT -> if (response.body() != null) {
                onSuccess(response.body())
            } else {
                onEmptyResponse(response.message())
            }
            423 -> onEmptyResponse(getErrorMessage(response))
            HttpURLConnection.HTTP_NOT_FOUND -> onFailed(Throwable(getErrorMessage(response)))
            HttpURLConnection.HTTP_UNAUTHORIZED -> onUnauthorized(Throwable(getErrorMessage(response)))
            else -> onFailed(Throwable(getErrorMessage(response)))
        }
    }

    private fun getErrorMessage(response: Response<T>?): String {
        if (response?.errorBody() == null) {
            return DEFAULT_ERROR_MSG
        }

        val jObjError: JSONObject
        try {
            var responseBody = response?.errorBody()!!.string()
            Log.d(TAG, "1 : " + responseBody)
            jObjError = JSONObject(responseBody)
        } catch (e: JSONException) {
            return DEFAULT_ERROR_MSG
        } catch (e: IOException) {
            return DEFAULT_ERROR_MSG
        }

        //gets message value which is returned by server
        var errorMessage = jObjError.optString("message", "")
        val errorMsg = jObjError.optString("error", "")
        if (!TextUtils.isEmpty(errorMessage)) {
            return errorMessage
        } else if (!TextUtils.isEmpty(errorMsg)) {
            return errorMsg
        } else {
            return DEFAULT_ERROR_MSG
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is NoConnectivityException) {
            //Add your code for displaying no network connection error
            onInternetFailed()
        } else {
            onFailed(t)
        }
    }

    abstract fun onSuccess(response: T?)

    abstract fun onUnauthorized(throwable: Throwable)

    abstract fun onFailed(throwable: Throwable)

    abstract fun onInternetFailed()

    abstract fun onEmptyResponse(message: String)

    companion object {
        private val TAG = "RemoteCallback"

        // Default error message
        private val DEFAULT_ERROR_MSG = "Sorry we are unable to reach server at this time."
    }

}
