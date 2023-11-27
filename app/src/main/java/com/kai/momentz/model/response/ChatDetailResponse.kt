package com.kai.momentz.model.response

import com.google.gson.annotations.SerializedName

data class ChatDetailResponse(

	@field:SerializedName("data")
	val data: List<ChatDetailListDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ChatDetailListDataItem(

	@field:SerializedName("id_chat")
	val idChat: Int? = null,

	@field:SerializedName("sender_username")
	val senderUsername: String? = null,

	@field:SerializedName("sent_at")
	val sentAt: String? = null,

	@field:SerializedName("is_read")
	val isRead: Int? = null,

	@field:SerializedName("sender_profile_picture")
	val senderProfilePicture: String? = null,

	@field:SerializedName("receiver_profile_picture")
	val receiverProfilePicture: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("id_receiver")
	val idReceiver: Int? = null,

	@field:SerializedName("id_sender")
	val idSender: Int? = null,

	@field:SerializedName("receiver_username")
	val receiverUsername: String? = null
)
