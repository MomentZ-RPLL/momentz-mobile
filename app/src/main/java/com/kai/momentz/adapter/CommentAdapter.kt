package com.kai.momentz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.momentz.R
import com.kai.momentz.model.response.CommentsDetailItem
import com.kai.momentz.model.response.CommentsItem
import com.kai.momentz.utils.getDate
import com.kai.momentz.utils.getTime

class CommentAdapter(private val listComment: List<CommentsDetailItem>, private var listener: CommentListener
) : RecyclerView.Adapter<CommentAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgProfile : ImageView = itemView.findViewById(R.id.img_user_profile)
        private var username : TextView = itemView.findViewById(R.id.comment_username)
        private var time: TextView = itemView.findViewById(R.id.time_comment)
        private var comment: TextView = itemView.findViewById(R.id.comment_user)

        @SuppressLint("SetTextI18n")
        fun bind(listCommentItem: CommentsDetailItem?, listener : CommentListener) {
            Glide.with(itemView.context)
                .load(listCommentItem!!.profilePicture)
                .into(imgProfile)

            username.text = listCommentItem.username
            time.text = "${getDate(listCommentItem.createdAt!!)}, ${getTime(listCommentItem.createdAt)} "
            comment.text=listCommentItem.comment

            username.setOnClickListener{
                listener.onUsernameClicked(listCommentItem.idUser ?: 0, listCommentItem.username!!, itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.comment_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount() = listComment.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listComment[position], listener)
    }

    interface CommentListener{
        fun onUsernameClicked(id: Int, username:String, itemView: View)
    }
}