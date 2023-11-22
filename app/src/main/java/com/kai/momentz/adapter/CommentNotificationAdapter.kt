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
import com.kai.momentz.model.response.CommentNotificationDataItem

class CommentNotificationAdapter(private val listCommentNotification: List<CommentNotificationDataItem>,
                                 private val fragmentManager: FragmentManager?) : RecyclerView.Adapter<CommentNotificationAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var postPhoto: ImageView = itemView.findViewById(R.id.img_post_photo)
        private var profilePhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
        private var username: TextView = itemView.findViewById(R.id.notif_username)
        private var activity: TextView = itemView.findViewById(R.id.activity)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(listNotificationItem: CommentNotificationDataItem?, fragmentManager: FragmentManager?){
            Glide.with(itemView.context)
                .load(listNotificationItem!!.postMedia)
                .into(postPhoto)

            Glide.with(itemView.context)
                .load(listNotificationItem.profilePicture)
                .into(profilePhoto)


//            for (i in followingItem){
//                if (listFollowItem.username == i.username){
//                    follow.visibility = View.INVISIBLE
//                    following.visibility = View.VISIBLE
//                    break
//                }
//            }

            username.text = listNotificationItem.username
            time.text = listNotificationItem.createdAt

            activity.text = itemView.context.getString(R.string.commented_on_your_post)




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
        holder.bind(listCommentNotification[position], fragmentManager)
    }

    override fun getItemCount() = listCommentNotification.size
}