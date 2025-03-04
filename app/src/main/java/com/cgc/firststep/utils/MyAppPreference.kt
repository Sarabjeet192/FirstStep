package com.cgc.firststep.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class MyAppPreference {

    companion object{

        private val PREF_NAME = "first_step"

        fun putBoolPreference(context: Context, status: Boolean, mKey : String) {
            val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(mKey, status)
            editor.apply()
        }

        fun putStringPreference(context: Context, mValue: String, mKey : String) {
            val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(mKey, mValue)
            editor.apply()
        }


        fun getBoolPreference(context: Context, mValue: String): Boolean {
            val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return preferences.getBoolean(mValue, false)
        }

        fun getStringPreference(context: Context, mKey: String): String? {
            val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return preferences.getString(mKey, "")
        }



        fun clearUserData(context: Context) {
            val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        }


        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

    }
}