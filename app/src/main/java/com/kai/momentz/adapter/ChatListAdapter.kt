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
import com.kai.momentz.model.response.ChatListDataItem

class ChatListAdapter (private val chatList: List<ChatListDataItem>, private val listener: ChatListAdapterListener
) : RecyclerView.Adapter<ChatListAdapter.ListViewHolder>()  {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
        private var username: TextView = itemView.findViewById(R.id.chat_username)
        private var chat: TextView = itemView.findViewById(R.id.chat)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(listChatItem: ChatListDataItem?, listener: ChatListAdapterListener){

            Glide.with(itemView.context)
                .load( listChatItem!!.otherProfilePicture)
                .into(imgPhoto)

            username.text = listChatItem.otherUsername
            chat.text = listChatItem.message
            time.text = listChatItem.sentAt

            itemView.setOnClickListener{
                listener.onViewClicked(listChatItem.otherUsername ?: "", itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(chatList[position], listener)
    }

    interface ChatListAdapterListener {
        fun onViewClicked(username: String, itemView: View)
    }

}