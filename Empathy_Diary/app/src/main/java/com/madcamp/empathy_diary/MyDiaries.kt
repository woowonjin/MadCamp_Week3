package com.madcamp.empathy_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyDiaries : AppCompatActivity() {

    private var retrofitClient = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_diaries)

        var feedListMy = arrayListOf<Item_feed>()

        val mAdapter = FeedAdapter(this, feedListMy)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_my)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter

        val call = retrofitClient.apiService.getMyDiary(FirebaseAuth.getInstance().uid.toString())
        call!!.enqueue(object : Callback<ArrayList<Item_feed>> {
            override fun onFailure(call: Call<ArrayList<Item_feed>>, t: Throwable) {
                Log.d("Error", "Get Feeds Error")
            }

            override fun onResponse(
                    call: Call<ArrayList<Item_feed>>,
                    response: Response<ArrayList<Item_feed>>
            ) {
                if (response.isSuccessful) {
                    val json_arr = response.body()
                    if (json_arr != null) {
                        for (item: Item_feed in json_arr) {
                            val text = item.feed_context
                            val date = item.feed_date
                            val likes = item.feed_likes
                            val pk = item.feed_pk
                            val emotion = item.emotion
                            val percent = item.percent
                            mAdapter?.addItem(Item_feed(date, text, likes, pk, emotion, percent))
                        }
                    }

                }
            }

        })

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout_my)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            mAdapter.remove_all()
            val call2 = retrofitClient.apiService.getMyDiary(FirebaseAuth.getInstance().uid.toString())
            call2!!.enqueue(object : Callback<ArrayList<Item_feed>> {
                override fun onFailure(call: Call<ArrayList<Item_feed>>, t: Throwable) {
                    Log.d("Error", "Get Feeds Error")
                }

                override fun onResponse(
                        call: Call<ArrayList<Item_feed>>,
                        response: Response<ArrayList<Item_feed>>
                ) {
                    if (response.isSuccessful) {
                        val json_arr = response.body()
                        if (json_arr != null) {
                            for (item: Item_feed in json_arr) {
                                val text = item.feed_context
                                val date = item.feed_date
                                val likes = item.feed_likes
                                val pk = item.feed_pk
                                val emotion = item.emotion
                                val percent = item.percent
                                mAdapter?.addItem(Item_feed(date, text, likes, pk, emotion, percent))
                            }
                        }

                    }
                }

            })

            Log.d("refresh", "refresh")
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@MyDiaries, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}