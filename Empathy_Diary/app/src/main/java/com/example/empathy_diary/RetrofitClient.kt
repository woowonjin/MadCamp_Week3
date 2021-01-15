package com.example.empathy_diary

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class RetrofitClient {
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()
    //서버 주소

    private val BASE_URL = "https://b93367aa7b7b.ngrok.io"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    public val apiService = retrofit.create(ApiService::class.java)
}