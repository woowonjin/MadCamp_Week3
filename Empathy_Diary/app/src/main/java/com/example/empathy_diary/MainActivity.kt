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

class MainActivity : AppCompatActivity() {

    private var googleSignInClient : GoogleSignInClient? = null

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