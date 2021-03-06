package com.madcamp.empathy_diary

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedAdapter(val context: Context, val feedList: ArrayList<Item_feed>):RecyclerView.Adapter<FeedAdapter.Holder>(){
    var feeds = feedList
    var retrofitClient = RetrofitClient()

//    //클릭 인터페이스 정의
//    interface ItemDoubleClickListener {
//        fun onDoubleClick(view: View, position: Int)
//    }
//
//    //클릭리스너 선언
//    private lateinit var itemDoubleClickListner: ItemDoubleClickListener
//
//    //클릭리스너 등록 매소드
//    fun setItemClickListener(itemDoubleClickListener: ItemDoubleClickListener) {
//        this.itemDoubleClickListner = itemDoubleClickListener
//    }
//

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val feedDate = itemView?.findViewById<TextView>(R.id.feed_date)
        val feedContext = itemView?.findViewById<TextView>(R.id.feed_context)
        val feedLike = itemView?.findViewById<TextView>(R.id.likes_count)
        val itemView_on = itemView
        val emotion = itemView?.findViewById<TextView>(R.id.emotion)
        val like_image = itemView?.findViewById<ImageView>(R.id.like_image)
        fun bind(feed:Item_feed, context: Context){
            feedDate?.text = feed.feed_date
            feedContext?.text = feed.feed_context
            feedLike?.text = feed.feed_likes.toString()
            like_image?.setColorFilter(Color.parseColor("#FF6F6E"))
            emotion?.text = feed.emotion + " " + feed.percent.toString() + "%"
        }
    }

    fun addItem(item : Item_feed){
        feeds.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    fun remove_all(){
        feeds.clear()
        notifyDataSetChanged()
    }

    fun reviseItem(position: Int, value: Int){
        feeds[position].feed_likes = feeds[position].feed_likes.toInt() + value
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(feeds[position], context)
        holder.itemView_on.setOnClickListener(
                DoubleClickListener(
                        callback = object : DoubleClickListener.Callback{
                            override fun doubleClicked(){
                                val uid = FirebaseAuth.getInstance().uid!!
                                val pk = feeds[position].feed_pk
                                val call = retrofitClient.apiService.like(Like(uid, pk))
                                call!!.enqueue(object: Callback<String> {
                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        Log.d("Error", "Post Like Error")
                                    }

                                    override fun onResponse(call: Call<String>, response: Response<String>) {
                                        if(response.isSuccessful) {
                                            Log.d("Success", "Post Like Success")
                                            if(response.body().toString() == "Create"){
                                                //add 1 to likes
                                                reviseItem(position, 1)
                                                Toast.makeText(context, "게시물에 공감하였습니다.", Toast.LENGTH_SHORT).show()
                                            }
                                            else if(response.body().toString() == "Delete"){ //Delete
                                                reviseItem(position, -1)
                                                Toast.makeText(context, "게시물에 공감을 취소하였습니다.", Toast.LENGTH_SHORT).show()
                                            }
                                            else{
                                                Log.d("Error", "Server Error")
                                            }
                                        }
                                    }

                                })
                            }
                        }
                ))

    }


}