package com.example.empathy_diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView

class FeedAdapter(val context: Context, val feedList: ArrayList<Item_feed>):RecyclerView.Adapter<FeedAdapter.Holder>(){

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val feedDate = itemView?.findViewById<TextView>(R.id.feed_date)
        val feedContext = itemView?.findViewById<TextView>(R.id.feed_context)

        fun bind(feed:Item_feed, context: Context){
            feedDate?.text = feed.feed_date
            feedContext?.text = feed.feed_context
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(feedList[position], context)
    }


}