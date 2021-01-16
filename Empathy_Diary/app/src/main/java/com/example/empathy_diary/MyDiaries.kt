package com.example.empathy_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyDiaries : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_diaries)
    }

    override fun onBackPressed() {
        val intent = Intent(this@MyDiaries, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}