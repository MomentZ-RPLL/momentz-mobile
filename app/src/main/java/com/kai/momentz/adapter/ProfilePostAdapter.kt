package com.kai.momentz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.PostsItem

class ProfilePostAdapter(private val listPost: List<PostsItem?>) : RecyclerView.Adapter<ProfilePostAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.image)

        fun bind(listPostItem:PostsItem?){
            Glide.with(itemView.context)
                .load( listPostItem!!.postMedia)
                .into(imgPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPost.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listPost[position])
    }
}