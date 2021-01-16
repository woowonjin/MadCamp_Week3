package com.example.empathy_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class WriteDiary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        setContentView(R.layout.activity_write_diary)

        //        val write = findViewById<EditText>(R.id.writeDiary)
//
//        write.setOnKeyListener{ v, keyCode, event ->
//            if (event.action == KeyEvent.ACTION_DOWN && keyCode == 66) {
//                Toast.makeText(this, write.text, Toast.LENGTH_LONG).show()
//            }
//
//            true
//        }

        findViewById<TextView>(R.id.textView_showDate).text = intent.getStringExtra("date")

//        findViewById<TextView>(R.id.textView_showDate).text = "123213"
    }


    override fun onBackPressed() {

        val intent = Intent(this, Calendar::class.java)
        startActivity(intent)
        finish()
    }
}