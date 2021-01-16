package com.example.empathy_diary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    private var retrofitClient = RetrofitClient()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

//        activity?.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        val intent = Intent(activity, WriteDiary::class.java)

        val date = Date(System.currentTimeMillis())
        val mDate = SimpleDateFormat("yyyy/MM/dd")
        var getTodayDate = mDate.format(date)

        intent.putExtra("date", getTodayDate)
        val call1 = retrofitClient.apiService.getDayDiary(FirebaseAuth.getInstance().uid.toString(), getTodayDate)
        call1!!.enqueue(object : Callback<Item_feed> {
            override fun onFailure(call: Call<Item_feed>, t: Throwable) {
                Log.d("Error", "Get Day Diary Error")
                view.findViewById<ScrollView>(R.id.day_scrollView).visibility = ScrollView.INVISIBLE
                view.findViewById<TextView>(R.id.textView_today).visibility = TextView.VISIBLE
            }

            override fun onResponse(call: Call<Item_feed>, response: Response<Item_feed>) {
                if(response.isSuccessful){
                    Log.d("Success", "Get Day Diary Success")
                    view.findViewById<ScrollView>(R.id.day_scrollView).visibility = ScrollView.VISIBLE
                    view.findViewById<TextView>(R.id.textView_today).visibility = TextView.INVISIBLE
                    val item : Item_feed = response.body() as Item_feed
                    view.findViewById<TextView>(R.id.day_date).text = item.feed_date
                    view.findViewById<TextView>(R.id.day_emotion).text = item.emotion + " " + item.percent.toString() + "%"
                    view.findViewById<TextView>(R.id.day_context).text = item.feed_context
                    view.findViewById<ImageView>(R.id.day_like_image).setColorFilter(Color.parseColor("#FF6F6E"))
                    view.findViewById<TextView>(R.id.day_likes_count).text = item.feed_likes.toString()
                }
            }

        })

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            var month_temp = ""
            month_temp = if(month < 9){
                "0" + (month+1).toString()
            } else{
                (month+1).toString()
            }

            var day_temp = ""
            day_temp = if(dayOfMonth < 10){
                "0"+dayOfMonth.toString()
            } else {
                dayOfMonth.toString()
            }

            val result_date = "$year/$month_temp/$day_temp"
            val call = retrofitClient.apiService.getDayDiary(FirebaseAuth.getInstance().uid.toString(), result_date)
            call!!.enqueue(object : Callback<Item_feed> {
                override fun onFailure(call: Call<Item_feed>, t: Throwable) {
                    Log.d("Error", "Get Day Diary Error")
                    view.findViewById<ScrollView>(R.id.day_scrollView).visibility = ScrollView.INVISIBLE
                    view.findViewById<TextView>(R.id.textView_today).visibility = TextView.VISIBLE
                }

                override fun onResponse(call: Call<Item_feed>, response: Response<Item_feed>) {
                    if(response.isSuccessful){
                        Log.d("Success", "Get Day Diary Success")
                        view.findViewById<ScrollView>(R.id.day_scrollView).visibility = ScrollView.VISIBLE
                        view.findViewById<TextView>(R.id.textView_today).visibility = TextView.INVISIBLE
                        val item : Item_feed = response.body() as Item_feed
                        view.findViewById<TextView>(R.id.day_date).text = item.feed_date
                        view.findViewById<TextView>(R.id.day_emotion).text = item.emotion + " " + item.percent.toString() + "%"
                        view.findViewById<TextView>(R.id.day_context).text = item.feed_context
                        view.findViewById<ImageView>(R.id.day_like_image).setColorFilter(Color.parseColor("#FF6F6E"))
                        view.findViewById<TextView>(R.id.day_likes_count).text = item.feed_likes.toString()
                    }
                }

            })
            intent.putExtra("date", "$year/$month_temp/$day_temp")


        }

        view.findViewById<TextView>(R.id.textView_today).setOnClickListener {
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

        return view
    }
    lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
//
//    override fun onDetach() {
//        super.onDetach()
//        callback.remove()
//    }

}