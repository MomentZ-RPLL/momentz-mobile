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
import com.kai.momentz.model.response.NotificationDataItem

class NotificationAdapter(private val listNotification: List<NotificationDataItem>,
                          private val fragmentManager: FragmentManager?,
                          private val index: Int) : RecyclerView.Adapter<NotificationAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var postPhoto: ImageView = itemView.findViewById(R.id.img_post_photo)
        private var username: TextView = itemView.findViewById(R.id.notif_username)
        private var activity: TextView = itemView.findViewById(R.id.activity)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(listNotificationItem: NotificationDataItem?, fragmentManager: FragmentManager?, index: Int){
            Glide.with(itemView.context)
                .load(listNotificationItem!!.postMedia)
                .into(postPhoto)

//            for (i in followingItem){
//                if (listFollowItem.username == i.username){
//                    follow.visibility = View.INVISIBLE
//                    following.visibility = View.VISIBLE
//                    break
//                }
//            }

            username.text = listNotificationItem.username
            time.text = listNotificationItem.createdAt
            when (index) {
                1 -> activity.text = itemView.context.getString(R.string.liked_your_post)
                2 -> activity.text = itemView.context.getString(R.string.start_following_you)
                3 -> activity.text = itemView.context.getString(R.string.start_follow_you)
                else -> {

                }
            }


            itemView.setOnClickListener {
//                val newFragment = ProfileFragment()
//                val bundle = Bundle()
//                bundle.putString("username", listNotificationItem.username)
//                newFragment.arguments = bundle
//
//                fragmentManager!!.beginTransaction()
//                    .replace(R.id.frame_container, newFragment)
//                    .addToBackStack(null)
//                    .commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listNotification[position], fragmentManager, index)
    }

    override fun getItemCount() = listNotification.size
}