package com.kai.momentz.adapter

import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.TimelineDataItem
import com.kai.momentz.utils.getDate
import com.kai.momentz.utils.getTime
import com.kai.momentz.view.comment.CommentFragment


class TimelineAdapter (private val listTimeline: List<TimelineDataItem>, private val fragmentManager : FragmentManager?) : RecyclerView.Adapter<TimelineAdapter.ListViewHolder>(){


    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var profilePhoto: ImageView = itemView.findViewById(R.id.profile_picture_photo)
        private var username : TextView = itemView.findViewById(R.id.username)
        private var postPhoto: ImageView = itemView.findViewById(R.id.ivPostmage)
        private var caption : TextView = itemView.findViewById(R.id.caption)
        private var time : TextView = itemView.findViewById(R.id.time)

        fun bind(listPostItem: TimelineDataItem, fragmentManager: FragmentManager?){
            Glide.with(itemView.context)
                .load(listPostItem.postmedia)
                .into(postPhoto)

            Log.d("tess", listPostItem.postmedia!!)

            Glide.with(itemView.context)
                .load(listPostItem.profilePicture)
                .into(profilePhoto)

            time.text = "${getDate(listPostItem.createdAt!!)}, ${getTime(listPostItem.createdAt)} "
            username.text = listPostItem.username
            caption.text = listPostItem.caption

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, CommentFragment::class.java)
                intent.putExtra("Comment Fragment", listPostItem.username)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_post_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount() = listTimeline.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listTimeline[position],fragmentManager)
    }


}


