package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class TimelineResponse(

	@field:SerializedName("data")
	val data: List<TimelineDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
data class TimelineDataItem(

	@field:SerializedName("comments")
	val comments: List<CommentsItem?>? = null,

	@field:SerializedName("like_count")
	val likeCount: Int? = null,

	@field:SerializedName("post_media")
	val postmedia: String? = null,

	@field:SerializedName("caption")
	val caption: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("username")
	val username: String? = null


)
