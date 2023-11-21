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
import com.kai.momentz.model.response.LikeNotificationDataItem

class LikeNotificationAdapter(private val listNotification: List<LikeNotificationDataItem>,
                              private val fragmentManager: FragmentManager?) : RecyclerView.Adapter<LikeNotificationAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var postPhoto: ImageView = itemView.findViewById(R.id.img_post_photo)
        private var username: TextView = itemView.findViewById(R.id.notif_username)
        private var activity: TextView = itemView.findViewById(R.id.activity)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(listNotificationItem: LikeNotificationDataItem?, fragmentManager: FragmentManager?){
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
            activity.text = itemView.context.getString(R.string.liked_your_post)

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
        holder.bind(listNotification[position], fragmentManager)
    }

    override fun getItemCount() = listNotification.size
}