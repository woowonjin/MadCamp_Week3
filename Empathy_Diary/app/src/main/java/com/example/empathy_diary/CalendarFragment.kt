package com.example.empathy_diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

//        activity?.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        val intent = Intent(activity, WriteDiary::class.java)

        val date = Date(System.currentTimeMillis())
        val mDate = SimpleDateFormat("yyyy/MM/dd")
        var getTodayDate = mDate.format(date)

        intent.putExtra("date", getTodayDate)


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