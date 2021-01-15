package com.example.empathy_diary

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/login/")
    fun login(@Body user : User): Call<String>

    @GET("/diaries/feeds/")
    fun getFeeds() : Call<ArrayList<Item_feed>>

    @POST("/likes/")
    fun like(@Body like : Like) : Call<String>
}