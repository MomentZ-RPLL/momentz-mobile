package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class PostDetailResponse(

	@field:SerializedName("data")
	val data: PostDetailData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PostDetailData(

	@field:SerializedName("comments")
	val comments: List<CommentsDetailItem?>? = null,

	@field:SerializedName("id_post")
	val idPost: Int? = null,

	@field:SerializedName("post_media")
	val postMedia: String? = null,

	@field:SerializedName("caption")
	val caption: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("lat")
	val lat: Any? = null,

	@field:SerializedName("likes")
	val likes: List<Any?>? = null
)

data class CommentsDetailItem(

	@field:SerializedName("is_notified")
	val isNotified: Int? = null,

	@field:SerializedName("id_post")
	val idPost: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("id_comment")
	val idComment: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
