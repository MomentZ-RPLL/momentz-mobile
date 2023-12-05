package com.kai.momentz.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.model.response.FollowNotificationDataItem
import com.kai.momentz.view.profile.ProfileFragment

class FollowNotificationAdapter(private val listFollowNotification: List<FollowNotificationDataItem>,
                                private val fragmentManager: FragmentManager?,
                                private val following: List<FollowItem>,
                                private val listener: FollowNotificationAdapterListener) : RecyclerView.Adapter<FollowNotificationAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var postPhoto: ImageView = itemView.findViewById(R.id.img_post_photo)
        private var profilePhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
        private var username: TextView = itemView.findViewById(R.id.notif_username)
        private var activity: TextView = itemView.findViewById(R.id.activity)
        private var time: TextView = itemView.findViewById(R.id.time)
        private var follow: Button = itemView.findViewById(R.id.follow)
        private var following: Button = itemView.findViewById(R.id.following)
        private var length: Int = 0
        private var space: String = "     "
        fun bind(listNotificationItem: FollowNotificationDataItem?, fragmentManager: FragmentManager?, followingItem: List<FollowItem>, listener: FollowNotificationAdapterListener){

            postPhoto.visibility = View.GONE
            follow.visibility = View.VISIBLE
            for (i in followingItem){
                if (listNotificationItem!!.username == i.username){
                    follow.visibility = View.GONE
                    following.visibility = View.VISIBLE
                    break
                }
            }

            Glide.with(itemView.context)
                .load(listNotificationItem!!.profilePicture)
                .into(profilePhoto)

            length = listNotificationItem.username!!.length
            for(i in 0..length){
                space += " "
            }

            username.text = listNotificationItem.username
            time.text = listNotificationItem.followedAt
            activity.text = "$space${itemView.context.getString(R.string.start_follow_you)}"

            follow.setOnClickListener {
                listener.onFollowClicked(listNotificationItem.userId ?: 0, itemView)
            }

            following.setOnClickListener {
                listener.onFollowingClicked(listNotificationItem.userId ?: 0, itemView)
            }

            itemView.setOnClickListener {
                val newFragment = ProfileFragment()
                val bundle = Bundle()
                bundle.putString("username", listNotificationItem.username)
                bundle.putString("id", listNotificationItem.userId.toString())
                newFragment.arguments = bundle

                fragmentManager!!.beginTransaction()
                    .replace(R.id.frame_container, newFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowNotification[position], fragmentManager, following, listener)
    }

    interface FollowNotificationAdapterListener {
        fun onFollowClicked(id:Int, itemView: View)
        fun onFollowingClicked(id:Int, itemView: View)
    }

    override fun getItemCount() = listFollowNotification.size
}