package com.kai.momentz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import com.kai.momentz.model.response.DataItem
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.CommentNotificationDataItem
import com.kai.momentz.model.response.Data


class TimeLineAdapter (private val listTimeline: List<DataItem>, private val fragmentManager : FragmentManager?) : RecyclerView.Adapter<TimeLineAdapter.ListViewHolder>(){


    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var profilePhoto: ImageView = itemView.findViewById(R.id.profile_picture_photo)
        private var username : TextView = itemView.findViewById(R.id.username)
        private var postPhoto: ImageView = itemView.findViewById(R.id.ivPostmage)
        private var caption : TextView = itemView.findViewById(R.id.caption)

        fun bind(listPostItem: DataItem?, fragmentManager: FragmentManager?){
            Glide.with(itemView.context)
                .load(listPostItem!!.postmedia)
                .into(postPhoto)

            Glide.with(itemView.context)
                .load(listPostItem.profilePicture)
                .into(profilePhoto)

            username.text = listPostItem.username
            caption.text = listPostItem.caption
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