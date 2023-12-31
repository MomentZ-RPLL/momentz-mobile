package com.kai.momentz.adapter

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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
import com.kai.momentz.model.response.TimelineDataItem
import com.kai.momentz.utils.getDate
import com.kai.momentz.utils.getTime
import com.kai.momentz.view.comment.CommentFragment


class TimelineAdapter (private val listTimeline: List<TimelineDataItem>, private val fragmentManager : FragmentManager?, private val like : List<TimelineDataItem>,private var listener:LikeListener) : RecyclerView.Adapter<TimelineAdapter.ListViewHolder>(){


    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var profilePhoto: ImageView = itemView.findViewById(R.id.profile_picture_photo)
        private var username : TextView = itemView.findViewById(R.id.username)
        private var postPhoto: ImageView = itemView.findViewById(R.id.ivPostmage)
        private var caption : TextView = itemView.findViewById(R.id.caption)
        private var time : TextView = itemView.findViewById(R.id.time)
        private var like : ImageView = itemView.findViewById(R.id.like)
        private var unlike : ImageView = itemView.findViewById(R.id.unlike)


        fun bind(listPostItem: TimelineDataItem, fragmentManager: FragmentManager?, listLikeItem: List<TimelineDataItem>,listener : LikeListener){
            Glide.with(itemView.context)
                .load(listPostItem!!.postMedia)
                .into(postPhoto)


            Glide.with(itemView.context)
                .load(listPostItem.profilePicture)
                .into(profilePhoto)

            for (i in listLikeItem){
                if (listPostItem.username == i.username){
                    unlike.visibility = View.GONE
                    like.visibility = View.VISIBLE

                }
            }

            time.text = "${getDate(listPostItem.createdAt!!)}, ${getTime(listPostItem.createdAt)} "
            username.text = listPostItem.username
            caption.text = listPostItem.caption

            itemView.setOnClickListener {
                val newFragment = CommentFragment()
                val bundle = Bundle()
                bundle.putString("idPost", listPostItem.idPost.toString())
                bundle.putString("profileImage", listPostItem.profilePicture)
                bundle.putString("username", listPostItem.username)
                newFragment.arguments = bundle

                fragmentManager!!.beginTransaction()
                    .replace(R.id.frame_container,newFragment)
                    .addToBackStack(null)
                    .commit()
            }

            like.setOnClickListener{
                listener.onLikeClicked(listPostItem.idPost ?: 0, itemView)
            }
            unlike.setOnClickListener{
                listener.onUnlikeClicked(listPostItem.idPost ?: 0, itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_post_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount() = listTimeline.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listTimeline[position],fragmentManager, like ,listener )
    }

    interface LikeListener{
        fun onLikeClicked(id: Int, itemView: View)

        fun onUnlikeClicked(id:Int, itemView:View)
    }

}


