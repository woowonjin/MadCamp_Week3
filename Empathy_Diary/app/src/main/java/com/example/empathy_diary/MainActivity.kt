package com.example.empathy_diary


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.reginald.swiperefresh.CustomSwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private var googleSignInClient : GoogleSignInClient? = null
    private var retrofitClient = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("442686102202-f37u9glbti9btdranin453kovcqq02dm.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        val logOutImg = findViewById<ImageView>(R.id.imageView_logout)

//        logOut_img.setOnClickListener(View.OnClickListener {
//            Firebase.auth.signOut()
//            googleSignInClient!!.signOut()
//            val intent = Intent(this, GoogleLoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        })

        val logOut: View.OnClickListener = View.OnClickListener{
            Firebase.auth.signOut()
            googleSignInClient!!.signOut()
            val intent = Intent(this, GoogleLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val logOutText = findViewById<TextView>(R.id.textView_logout)

        logOutImg.setOnClickListener(logOut)
        logOutText.setOnClickListener(logOut)

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout)

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            // 새로운 데이터 받아오기
        }



        val similarText = findViewById<TextView>(R.id.textView_Similar)
        similarText.setOnClickListener{
            val intent = Intent(this@MainActivity, SimilarDiary::class.java)
            startActivity(intent)
            finish()
        }

        val differentText = findViewById<TextView>(R.id.textView_Different)
        differentText.setOnClickListener {
            val intent = Intent(this@MainActivity, DifferentDiary::class.java)
            startActivity(intent)
            finish()
        }


        findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawer(Gravity.LEFT)

        var feedList = arrayListOf<Item_feed>()

        val mAdapter = FeedAdapter(this, feedList)

        val call = retrofitClient.apiService.getFeeds()
        call!!.enqueue(object: Callback<ArrayList<Item_feed>> {
            override fun onFailure(call: Call<ArrayList<Item_feed>>, t: Throwable) {
                Log.d("Error", "Get Feeds Error")
            }

            override fun onResponse(call: Call<ArrayList<Item_feed>>, response: Response<ArrayList<Item_feed>>) {
                if(response.isSuccessful){
                    val json_arr = response.body()
                    if (json_arr != null) {
                        for(item : Item_feed in json_arr){
                            val text = item.feed_context
                            val date = item.feed_date
                            val likes = item.feed_likes
                            val pk = item.feed_pk
                            val emotion = item.emotion
                            val percent = item.percent
                            mAdapter.addItem(Item_feed(date, text, likes, pk, emotion, percent))
                        }
                    }

                }
            }

        })

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_feed)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter

        findViewById<TextView>(R.id.todayDiary).setOnClickListener(ShowCalendar())

        findViewById<TextView>(R.id.textView_myDiary).setOnClickListener(ShowMyDiaries())

//        val refreshLayout = findViewById<CustomSwipeRefreshLayout>(R.id.swipe_layout)
//
//        refreshLayout.setOnRefreshListener {
//            fun onRefresh(){
//                feedList.add(Item_feed("12/23", "눈\n이\n왔\n다"))
//            }
//
//            // 새로운 데이터 받아오기
//
//            refreshLayout.refreshComplete()
//        }

    }

    inner class ShowCalendar: View.OnClickListener{
        override fun onClick(v: View?) {
            val intent = Intent(this@MainActivity, Calendar::class.java)
            startActivity(intent)
            finish()
        }
    }

    inner class ShowMyDiaries: View.OnClickListener{
        override fun onClick(v: View?){
            val intent = Intent(this@MainActivity, MyDiaries::class.java)
            startActivity(intent)
            finish()
        }
    }
}