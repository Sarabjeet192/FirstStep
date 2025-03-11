package com.cgc.firststep.network

import com.cgc.firststep.utils.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebAPIServiceFactory {

    fun makeServiceFactory(): WebAPIService {
        return makeServiceFactory(makeOkHttpClient())
    }

    private fun makeServiceFactory(okHttpClient: OkHttpClient): WebAPIService {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(WebAPIService::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient().newBuilder()
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(HTTP_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)


        httpClientBuilder.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            // Customize the request
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept-app-version", "1.0.0")
                .header("Accept-type", "1")
                .method(original.method, original.body)

            val request = requestBuilder.build()
            val response = chain.proceed(request)

            response
        })

        httpClientBuilder.addInterceptor(loggingInterceptor())
        return httpClientBuilder.build()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    companion object {
        private const val HTTP_READ_TIMEOUT = 200000
        private const val HTTP_WRITE_TIMEOUT = 200000
        private const val HTTP_CONNECT_TIMEOUT = 600000

        private var INSTANCE: WebAPIServiceFactory? = null

        fun newInstance(): WebAPIServiceFactory {
            if (INSTANCE == null) {
                INSTANCE =
                    WebAPIServiceFactory()
            }
            return INSTANCE as WebAPIServiceFactory
        }
    }

}