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
import com.kai.momentz.model.response.FollowNotificationDataItem

class FollowNotificationAdapter(private val listFollowNotification: List<FollowNotificationDataItem>,
                                private val fragmentManager: FragmentManager?) : RecyclerView.Adapter<FollowNotificationAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var postPhoto: ImageView = itemView.findViewById(R.id.img_post_photo)
        private var profilePhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
        private var username: TextView = itemView.findViewById(R.id.notif_username)
        private var activity: TextView = itemView.findViewById(R.id.activity)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(listNotificationItem: FollowNotificationDataItem?, fragmentManager: FragmentManager?){
            postPhoto.visibility = View.GONE

            Glide.with(itemView.context)
                .load(listNotificationItem!!.profilePicture)
                .into(profilePhoto)

            username.text = listNotificationItem.username
            time.text = listNotificationItem.followedAt
            activity.text = itemView.context.getString(R.string.start_follow_you)

            itemView.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowNotification[position], fragmentManager)
    }

    override fun getItemCount() = listFollowNotification.size
}