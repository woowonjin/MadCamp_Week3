package com.example.empathy_diary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawer(Gravity.LEFT)

        var feedList = arrayListOf<Item_feed>()
        feedList.add(Item_feed("12/20", "날씨가 좋았다"))

        feedList.add(Item_feed("12/21", "날씨가 흐렸다"))

        feedList.add(Item_feed("12/22", "비가\n 왔다"))

        feedList.add(Item_feed("12/23", "눈\n이\n왔\n다"))

        val mAdapter = FeedAdapter(this, feedList)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_feed)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter

        findViewById<TextView>(R.id.todayDiary).setOnClickListener(ButtonListener())

    }

    inner class ButtonListener: View.OnClickListener{
        override fun onClick(v: View?) {
            val intent = Intent(this@MainActivity, Calendar::class.java)
            startActivity(intent)
            finish()
        }
    }
}