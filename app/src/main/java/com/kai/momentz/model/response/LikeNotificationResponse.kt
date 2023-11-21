package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class LikeNotificationResponse(

	@field:SerializedName("data")
	val data: List<NotificationDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class NotificationDataItem(

	@field:SerializedName("id_post")
	val idPost: Int? = null,

	@field:SerializedName("post_media")
	val postMedia: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_like")
	val idLike: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
