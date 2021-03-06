package com.madcamp.empathy_diary

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteDiary : AppCompatActivity() {

    private var retrofitClient = RetrofitClient()
    var emoPercent : String = "50"
    var emotion : String = ""

    @SuppressLint("ResourceType")
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
            val call = retrofitClient.apiService.postDiary(PostDiary(text, date, is_visible, uid, emotion, emoPercent))
            Log.d("Text", text)
            if(text != "") {
                if(emotion == ""){
                    Toast.makeText(this, "감정을 선택하세요.", Toast.LENGTH_SHORT).show()
                }
                else {
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
            }
            else{
                Toast.makeText(this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

        })

        val chooseEmotion: View.OnClickListener = View.OnClickListener{

        }
        val happy_btn = findViewById<Button>(R.id.imageView_Happy)
        val depression_btn = findViewById<Button>(R.id.imageView_Depression)
        val anger_btn = findViewById<Button>(R.id.imageView_Anger)
        val anxiety_btn = findViewById<Button>(R.id.imageView_Anxiety)

        happy_btn.setOnClickListener(View.OnClickListener {
            emotion = "행복"
//            depression_btn.background = R.drawable
            happy_btn.background = ContextCompat.getDrawable(this, R.drawable.roundcorner)
//            happy_btn.setBackgroundColor(Color.rgb(225, 245, 196))
            depression_btn.setBackgroundColor(Color.TRANSPARENT)
            anger_btn.setBackgroundColor(Color.TRANSPARENT)
            anxiety_btn.setBackgroundColor(Color.TRANSPARENT)
        })

        depression_btn.setOnClickListener(View.OnClickListener {
            emotion = "슬픔"
            depression_btn.background = ContextCompat.getDrawable(this, R.drawable.roundcorner)
//            depression_btn.setBackgroundColor(Color.rgb(225, 245, 196))
            happy_btn.setBackgroundColor(Color.TRANSPARENT)
            anger_btn.setBackgroundColor(Color.TRANSPARENT)
            anxiety_btn.setBackgroundColor(Color.TRANSPARENT)
        })

        anger_btn.setOnClickListener(View.OnClickListener {
            emotion = "분노"
            anger_btn.background = ContextCompat.getDrawable(this, R.drawable.roundcorner)
//            anger_btn.setBackgroundColor(Color.rgb(225, 245, 196))
            depression_btn.setBackgroundColor(Color.TRANSPARENT)
            happy_btn.setBackgroundColor(Color.TRANSPARENT)
            anxiety_btn.setBackgroundColor(Color.TRANSPARENT)
        })

        anxiety_btn.setOnClickListener(View.OnClickListener {
            emotion = "불안"
            anxiety_btn.background = ContextCompat.getDrawable(this, R.drawable.roundcorner)
//            anxiety_btn.setBackgroundColor(Color.rgb(225, 245, 196))
            depression_btn.setBackgroundColor(Color.TRANSPARENT)
            anger_btn.setBackgroundColor(Color.TRANSPARENT)
            happy_btn.setBackgroundColor(Color.TRANSPARENT)
        })




        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar.progress = 50

        val textViewPercent = findViewById<TextView>(R.id.textView_Emotion_Percent)

        findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val padding = seekBar!!.paddingLeft + seekBar!!.paddingRight
                val sPos = seekBar!!.left + seekBar!!.paddingLeft
                val xPos: Int =
                    (seekBar!!.width - padding) * seekBar!!.progress / seekBar!!.max + sPos - textViewPercent.width/2
                Log.d("Position", xPos.toString())


                textViewPercent.translationX = xPos.toFloat()
                textViewPercent.text = seekBar!!.progress.toString()+"%"

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                val padding = seekBar!!.paddingLeft + seekBar!!.paddingRight
                val sPos = seekBar!!.left + seekBar!!.paddingLeft

                val xPos: Int =
                    (seekBar!!.width - padding) * seekBar!!.progress / seekBar!!.max + sPos - textViewPercent.width/2
                Log.d("Position", xPos.toString())

                textViewPercent.translationX = xPos.toFloat()
                textViewPercent.text = seekBar!!.progress.toString()+"%"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val padding = seekBar!!.paddingLeft + seekBar!!.paddingRight
                val sPos = seekBar!!.left + seekBar!!.paddingLeft

                val xPos: Int =
                    (seekBar!!.width - padding) * seekBar!!.progress / seekBar!!.max + sPos - textViewPercent.width/2
                Log.d("Position", xPos.toString())

                textViewPercent.translationX = xPos.toFloat()
                textViewPercent.text = seekBar!!.progress.toString()+"%"
                emoPercent = seekBar!!.progress.toString()


            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }

}