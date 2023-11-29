package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class CommentResponse(

	@field:SerializedName("data")
	val data: Datas? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CommentsItem(

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

data class Datas(

	@field:SerializedName("comments")
	val comments: List<CommentsItem?>? = null,

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
	val likes: List<LikesItem?>? = null
)

data class LikesItem(

	@field:SerializedName("is_notified")
	val isNotified: Int? = null,

	@field:SerializedName("id_post")
	val idPost: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id_like")
	val idLike: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
