package com.kai.momentz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.ChatDetailListDataItem
import com.kai.momentz.model.response.ChatListDataItem
import com.kai.momentz.utils.*

class ChatDetailListAdapter(private val chatDetailList: List<ChatDetailListDataItem>,
                            private val listener: ChatDetailListAdapterListener,
                            private val currentId: Int
): RecyclerView.Adapter<ChatDetailListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var imgPhoto: ImageView
        private lateinit var username: TextView
        private lateinit var chat: TextView
        private lateinit var time: TextView
        private lateinit var date: TextView
        fun bind(listChatDetailItem: ChatDetailListDataItem?, isSent: Boolean, listener: ChatDetailListAdapterListener){
            if(isSent){
                chat = itemView.findViewById(R.id.text_gchat_message_me)
                time = itemView.findViewById(R.id.text_gchat_timestamp_me)
                date = itemView.findViewById(R.id.text_gchat_date_me)

                chat.text = listChatDetailItem!!.message
                time.text = getTime(listChatDetailItem.sentAt!!)
                date.text = getDate(listChatDetailItem.sentAt)
            }else {
                imgPhoto = itemView.findViewById(R.id.image_gchat_profile_other)
                username = itemView.findViewById(R.id.text_gchat_user_other)
                chat = itemView.findViewById(R.id.text_gchat_message_other)
                time = itemView.findViewById(R.id.text_gchat_timestamp_other)
                date = itemView.findViewById(R.id.text_gchat_date_other)

                Glide.with(itemView.context)
                    .load( listChatDetailItem!!.senderProfilePicture)
                    .into(imgPhoto)

                username.text = listChatDetailItem.senderUsername
                chat.text = listChatDetailItem.message
                time.text = getTime(listChatDetailItem.sentAt!!)
                date.text = getDate(listChatDetailItem.sentAt)

                itemView.setOnClickListener{
                    listener.onProfileImageClicked(listChatDetailItem.senderUsername ?: "", itemView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutResId = if (viewType == SENT_MESSAGE_TYPE) {
            R.layout.sender_chat_detail_item
        } else {
            R.layout.receive_chat_detail_item
        }

        val view: View = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = chatDetailList.size

    override fun getItemViewType(position: Int): Int {

        return if (chatDetailList[position].idSender==currentId) {
            SENT_MESSAGE_TYPE
        } else {
            RECEIVED_MESSAGE_TYPE
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val isSent = chatDetailList[position].idSender==currentId
        holder.bind(chatDetailList[position], isSent, listener)
    }

    interface ChatDetailListAdapterListener {
        fun onProfileImageClicked(username: String, itemView: View)
    }

    companion object {
        const val SENT_MESSAGE_TYPE = 1
        const val RECEIVED_MESSAGE_TYPE = 2
    }

}