package com.technoactive.mymovieapp.repos

import android.util.Log
import com.technoactive.mymovieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OmdbRequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("apikey", BuildConfig.OMDB_API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader("Accept", "application/json")
            .build()

        Log.d("Luv", "$newRequest")

        return chain.proceed(newRequest)
    }
}
