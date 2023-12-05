package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class PostCommentResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
