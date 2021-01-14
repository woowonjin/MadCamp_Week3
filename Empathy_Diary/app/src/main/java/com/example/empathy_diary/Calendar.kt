package com.example.empathy_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

class Calendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

//        var intent: Intent
        var intent = Intent(this@Calendar, WriteDiary::class.java)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            var month_temp = ""
            month_temp = if(month < 9){
                "0" + (month+1).toString()
            } else{
                (month+1).toString()
            }
            intent.putExtra("date", "$year/$month_temp/$dayOfMonth")
        }

        findViewById<TextView>(R.id.textView_today).setOnClickListener(View.OnClickListener {
            startActivity(intent)

            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        })

//        class ClickListener: View.OnClickListener{
//            override fun onClick(v: View?) {
//
//
////            intent.putExtra("date", findViewById<CalendarView>(R.id.calendarView).date.toString())
////            intent.putExtra("date", "1202")
////            val intent = Intent(this@Calendar, WriteDiary::class.java)
//                startActivity(intent)
//
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//                finish()
//            }
//        }

    }


//    inner class ClickListener: View.OnClickListener{
//        override fun onClick(v: View?) {
//
//
////            intent.putExtra("date", findViewById<CalendarView>(R.id.calendarView).date.toString())
////            intent.putExtra("date", "1202")
////            val intent = Intent(this@Calendar, WriteDiary::class.java)
//            startActivity(intent)
//
//            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//            finish()
//        }
//    }

    override fun onBackPressed() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
//        super.onBackPressed()
    }
}