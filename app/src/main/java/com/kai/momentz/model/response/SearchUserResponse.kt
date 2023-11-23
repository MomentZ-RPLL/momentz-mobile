package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

	@field:SerializedName("data")
	val data: List<SearchDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SearchDataItem(

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
