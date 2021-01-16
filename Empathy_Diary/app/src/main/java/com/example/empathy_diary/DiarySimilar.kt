package com.example.empathy_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class DiarySimilar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_similar_diary)

        var feedList = arrayListOf<Item_feed>()

//
        val mAdapter = FeedAdapter(this, feedList)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_similar)



        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout_similar)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
//            feedList.add(Item_feed("12/23", "눈\n이\n왔\n다", 1, "d"))
            // 새로운 데이터 받아오기
            //
            //
//            mAdapter?.notifyDataSetChanged()

            Log.d("refresh", "refresh")
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@DiarySimilar, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}