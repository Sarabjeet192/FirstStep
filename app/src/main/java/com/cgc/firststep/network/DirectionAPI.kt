package com.cgc.firststep.network

import com.cgc.firststep.model.DirectionsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface DirectionsAPI {
    @GET
    fun getDirections(@Url url: String): Call<DirectionsResponse>
}
