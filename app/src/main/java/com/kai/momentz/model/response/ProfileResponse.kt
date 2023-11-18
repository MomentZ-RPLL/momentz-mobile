package com.kai.momentz.model.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ProfileResponse(

	@field:SerializedName("data")
	val data: DataProfile? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class DataProfile(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("following_count")
	val followingCount: Int? = null,

	@field:SerializedName("followers_count")
	val followersCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("posts")
	val posts: List<PostsItem?>? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable

@Parcelize
data class PostsItem(

	@field:SerializedName("id_post")
	val idPost: Int? = null,

	@field:SerializedName("post_media")
	val postMedia: String? = null,

	@field:SerializedName("caption")
	val caption: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null
) : Parcelable
