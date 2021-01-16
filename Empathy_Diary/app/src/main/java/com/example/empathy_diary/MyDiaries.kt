package com.example.empathy_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MyDiaries : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_diaries)

        var feedListMy = arrayListOf<Item_feed>()

        val mAdapter = FeedAdapter(this, feedListMy)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_my)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout_my)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false


            Log.d("refresh", "refresh")
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@MyDiaries, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}