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
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


//        val logOut = findViewById<Button>(R.id.logout)
//        logOut.setOnClickListener(View.OnClickListener {
//            Firebase.auth.signOut()
//            googleSignInClient!!.signOut()
//            val intent = Intent(this, GoogleLoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        })
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
                            mAdapter.addItem(Item_feed(date, text, likes, pk))
                        }
                    }

                }
            }

        })

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