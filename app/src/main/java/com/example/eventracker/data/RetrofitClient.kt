package com.example.eventracker.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofitServices : RetrofitServices by lazy {
        Retrofit.Builder()
            .baseUrl(RetrofitServices.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitServices::class.java)
    }
}