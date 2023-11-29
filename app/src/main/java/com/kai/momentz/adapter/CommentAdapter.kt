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
import com.kai.momentz.model.response.CommentsItem
import com.kai.momentz.utils.getDate

class CommentAdapter(private val listComment: List<CommentsItem>,
                     private val fragmentManager: FragmentManager?) : RecyclerView.Adapter<CommentAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgProfile : ImageView = itemView.findViewById(R.id.img_user_profile)
        private var username : TextView = itemView.findViewById(R.id.comment_username)
        private var time: TextView = itemView.findViewById(R.id.time_comment)
        private var comment: TextView = itemView.findViewById(R.id.comment_user)

        fun bind(listCommentItem: CommentsItem?, fragmentManager: FragmentManager?) {
            Glide.with(itemView.context)
                .load(listCommentItem!!.profilePicture)
                .into(imgProfile)
            username.text = listCommentItem.username
            time.text = listCommentItem.createdAt
            comment.text=listCommentItem.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.comment_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount() = listComment.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listComment[position],fragmentManager)
    }
}