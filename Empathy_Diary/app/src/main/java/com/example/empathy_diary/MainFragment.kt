package com.example.empathy_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

    private var googleSignInClient: GoogleSignInClient? = null
    private var retrofitClient = RetrofitClient()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val view = inflater.inflate(R.layout.fragment_main, container, false)


        var feedList = arrayListOf<Item_feed>()

        val mAdapter = context?.let { FeedAdapter(it, feedList) }

        val call = retrofitClient.apiService.getFeeds()
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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_feed)

        val linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter

        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            mAdapter?.remove_all()
            val call2 = retrofitClient.apiService.getFeeds()
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

        return view
    }


}

