package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class ChatListResponse(

	@field:SerializedName("data")
	val data: List<ChatListDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ChatListDataItem(

	@field:SerializedName("sent_at")
	val sentAt: String? = null,

	@field:SerializedName("is_read")
	val isRead: Int? = null,

	@field:SerializedName("other_username")
	val otherUsername: String? = null,

	@field:SerializedName("other_profile_picture")
	val otherProfilePicture: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("id_receiver")
	val idReceiver: Int? = null,

	@field:SerializedName("id_sender")
	val idSender: Int? = null
)
