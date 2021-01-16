package com.example.empathy_diary

import android.content.ClipData
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

    @POST("/diaries/diary/")
    fun postDiary(@Body diary: PostDiary) : Call<String>

    @GET("/diaries/similar_feeds/")
    fun getSimilarFeeds(@Query("uid") uid : String) : Call<ArrayList<Item_feed>>

    @GET("/diaries/diary/")
    fun getMyDiary(@Query("uid") uid : String) : Call<ArrayList<Item_feed>>

    @GET("/diaries/opposite_feeds/")
    fun getOppositeFeeds(@Query("uid") uid: String) : Call<ArrayList<Item_feed>>

    @GET("/diaries/day_diary/")
    fun getDayDiary(@Query("uid") uid: String, @Query("date") date: String) : Call<Item_feed>
}