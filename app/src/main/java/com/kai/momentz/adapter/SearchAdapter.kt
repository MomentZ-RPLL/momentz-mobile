package com.kai.momentz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.SearchDataItem
import com.kai.momentz.view.search.SearchFragment

class SearchAdapter(private val searchList: List<SearchDataItem>,
                    private val listener: SearchAdapterListener
) : RecyclerView.Adapter<SearchAdapter.ListViewHolder>()  {


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        private var username: TextView = itemView.findViewById(R.id.username)
        private var follow: Button = itemView.findViewById(R.id.follow)

        fun bind(listSearchItem: SearchDataItem?, listener: SearchAdapterListener){
            follow.visibility = View.GONE

            Glide.with(itemView.context)
                .load( listSearchItem!!.profilePicture)
                .into(imgPhoto)

            username.text = listSearchItem.username

            itemView.setOnClickListener{
                listener.onViewClicked(listSearchItem.username ?: "", itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(searchList[position], listener)
    }

    interface SearchAdapterListener {
        fun onViewClicked(username: String, itemView: View)
    }
}