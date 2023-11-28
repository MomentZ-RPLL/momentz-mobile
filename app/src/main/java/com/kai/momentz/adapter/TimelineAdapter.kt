package com.kai.momentz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.DataItem
import com.kai.momentz.utils.getDate
import com.kai.momentz.utils.getTime


class TimelineAdapter (private val listTimeline: List<DataItem>, private val fragmentManager : FragmentManager?) : RecyclerView.Adapter<TimelineAdapter.ListViewHolder>(){


    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var profilePhoto: ImageView = itemView.findViewById(R.id.profile_picture_photo)
        private var username : TextView = itemView.findViewById(R.id.username)
        private var postPhoto: ImageView = itemView.findViewById(R.id.ivPostmage)
        private var caption : TextView = itemView.findViewById(R.id.caption)
        private var time : TextView = itemView.findViewById(R.id.time)

        fun bind(listPostItem: DataItem?, fragmentManager: FragmentManager?){
            Glide.with(itemView.context)
                .load(listPostItem!!.postmedia)
                .into(postPhoto)

            Glide.with(itemView.context)
                .load(listPostItem.profilePicture)
                .into(profilePhoto)

            time.text = "${getDate(listPostItem.createdAt!!)}, ${getTime(listPostItem.createdAt)} "
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