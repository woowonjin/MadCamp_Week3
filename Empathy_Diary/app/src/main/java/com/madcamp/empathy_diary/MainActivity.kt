package com.madcamp.empathy_diary


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

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
            val intent = Intent(this@MainActivity, DiarySimilar::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }

        val differentText = findViewById<TextView>(R.id.textView_Different)
        differentText.setOnClickListener {
            val intent = Intent(this@MainActivity, DiaryDifferent::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }


        findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawer(Gravity.LEFT)

    }

    override fun onBackPressed() {

        if (vpSlider.currentItem == 0){
            super.onBackPressed()
            finish()
        }
        else{
            vpSlider.currentItem = 0
        }

    }
}