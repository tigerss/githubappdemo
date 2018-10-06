package com.sample.githubtrending

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private const val BASE_URL = "https://github-trending-api.now.sh"

    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()

    private val httpClient = OkHttpClient.Builder()

    fun <S> createService(
            serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}
