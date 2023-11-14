package com.kai.momentz.adapter

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.FollowItem
import com.kai.momentz.view.follow.FollowFragment
import com.kai.momentz.view.profile.ProfileFragment

class FollowAdapter(private val listFollow: List<FollowItem>,
                    private val fragmentManager: FragmentManager?) : RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        private var username: TextView = itemView.findViewById(R.id.name)

        fun bind(listFollowItem: FollowItem?, fragmentManager: FragmentManager?){
            Glide.with(itemView.context)
                .load(listFollowItem!!.profilePicture)
                .into(imgPhoto)

            username.text = listFollowItem.username
            itemView.setOnClickListener {
                val newFragment = ProfileFragment()
                val bundle = Bundle()
                bundle.putString("username", listFollowItem.username)
                newFragment.arguments = bundle

                fragmentManager!!.beginTransaction()
                    .replace(R.id.frame_container, newFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollow[position], fragmentManager)
    }

    override fun getItemCount() = listFollow.size
}