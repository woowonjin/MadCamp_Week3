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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.reginald.swiperefresh.CustomSwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private var googleSignInClient: GoogleSignInClient? = null
    private var retrofitClient = RetrofitClient()

    lateinit var vpSlider : ViewPager2
    lateinit var pagerAdapter : FragmentStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> MainFragment()
                    else -> CalendarFragment()
                }
            }
        }

//        val vpSlider = findViewById<ViewPager2>(R.id.ViewPager)
//        val pagerAdapter = ScreenSlidePagerAdapter(this)
        vpSlider = findViewById<ViewPager2>(R.id.ViewPager)
        pagerAdapter = ScreenSlidePagerAdapter(this)
        vpSlider.adapter = pagerAdapter

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("442686102202-f37u9glbti9btdranin453kovcqq02dm.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val logOut: View.OnClickListener = View.OnClickListener{
            Log.e("click?","click")
            Firebase.auth.signOut()
            googleSignInClient!!.signOut()
            val intent = Intent(this, GoogleLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val logOutImg = findViewById<ImageView>(R.id.imageView_logout)
        val logOutText = findViewById<TextView>(R.id.textView_logout)

        logOutImg.setOnClickListener(logOut)
        logOutText.setOnClickListener(logOut)

        val myDiariesText = findViewById<TextView>(R.id.textView_MyDiaries)
        myDiariesText.setOnClickListener {
            val intent = Intent(this@MainActivity, MyDiaries::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }

        val similarText = findViewById<TextView>(R.id.textView_Similar)
        similarText.setOnClickListener{
            val intent = Intent(this@MainActivity, SimilarDiary::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }

        val differentText = findViewById<TextView>(R.id.textView_Different)
        differentText.setOnClickListener {
            val intent = Intent(this@MainActivity, DifferentDiary::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }


        findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawer(Gravity.LEFT)

//        var feedList = arrayListOf<Item_feed>()
//
//        val mAdapter = FeedAdapter(this, feedList)
//
//        val call = retrofitClient.apiService.getFeeds()
//        call!!.enqueue(object: Callback<ArrayList<Item_feed>> {
//            override fun onFailure(call: Call<ArrayList<Item_feed>>, t: Throwable) {
//                Log.d("Error", "Get Feeds Error")
//            }
//
//            override fun onResponse(call: Call<ArrayList<Item_feed>>, response: Response<ArrayList<Item_feed>>) {
//                if(response.isSuccessful){
//                    val json_arr = response.body()
//                    if (json_arr != null) {
//                        for(item : Item_feed in json_arr){
//                            val text = item.feed_context
//                            val date = item.feed_date
//                            val likes = item.feed_likes
//                            val pk = item.feed_pk
//                            mAdapter.addItem(Item_feed(date, text, likes, pk))
//                        }
//                    }
//
//                }
//            }
//
//        })
    }

    override fun onBackPressed() {

        if (vpSlider.currentItem == 0){
            super.onBackPressed()
//            finish()
        }
        else{
            vpSlider.currentItem = 0
        }

    }
}