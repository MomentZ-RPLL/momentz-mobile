package com.kai.momentz.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.PostsItem
import com.kai.momentz.view.comment.CommentFragment

class ProfilePostAdapter(private val listPost: List<PostsItem?>, private val fragmentManager: FragmentManager?, private val username: String, private val profilePhoto: String) : RecyclerView.Adapter<ProfilePostAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.image)

        fun bind(listPostItem:PostsItem?, fragmentManager: FragmentManager?, username: String, profilePhoto: String){
            Glide.with(itemView.context)
                .load( listPostItem!!.postMedia)
                .into(imgPhoto)


            itemView.setOnClickListener {
                val newFragment = CommentFragment()
                val bundle = Bundle()
                bundle.putString("idPost", listPostItem.idPost.toString())
                bundle.putString("profileImage", profilePhoto)
                bundle.putString("username", username)
                newFragment.arguments = bundle

                fragmentManager!!.beginTransaction()
                    .replace(R.id.frame_container,newFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPost.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listPost[position], fragmentManager, username, profilePhoto)
    }
}