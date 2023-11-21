package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class CommentNotificationResponse(

	@field:SerializedName("data")
	val data: List<CommentNotificationDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CommentNotificationDataItem(

	@field:SerializedName("id_post")
	val idPost: Int? = null,

	@field:SerializedName("post_media")
	val postMedia: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_comment")
	val idComment: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
