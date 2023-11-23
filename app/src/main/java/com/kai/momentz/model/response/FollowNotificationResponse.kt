package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class FollowNotificationResponse(

	@field:SerializedName("data")
	val data: List<FollowNotificationDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class FollowNotificationDataItem(

	@field:SerializedName("followed_at")
	val followedAt: String? = null,

	@field:SerializedName("id_user")
	val userId: Int? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
