package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class TimelineResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
data class DataItem(

	@field:SerializedName("like_count")
	val likeCount: Int? = null,

	@field:SerializedName("id_post")
	val idPost: Int? = null,

	@field:SerializedName("post_media")
	val postMedia: String? = null,

	@field:SerializedName("caption")
	val caption: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
