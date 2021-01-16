package com.example.empathy_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteDiary : AppCompatActivity() {

    private var retrofitClient = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        setContentView(R.layout.activity_write_diary)

        val diary_date = findViewById<TextView>(R.id.textView_showDate)
        diary_date.text = intent?.getStringExtra("date").toString()

        val submit_btn = findViewById<ImageView>(R.id.imageView_postDiary)
        val edit_text = findViewById<EditText>(R.id.editText_todayDiary)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)

        submit_btn.setOnClickListener(View.OnClickListener {
            val text = edit_text.text.toString()
            val date = diary_date.text.toString()
            val is_visible = checkBox.isChecked
            val uid = FirebaseAuth.getInstance().uid.toString()
            val call = retrofitClient.apiService.postDiary(PostDiary(text, date, is_visible, uid))
            Log.d("Text", text)
            if(text != "") {
                call!!.enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("Error", "Post Diary Error")
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            Log.d("Success", "Post Diary Success")
                            finish()
                        }
                    }
                })
            }
            else{
                Toast.makeText(this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}