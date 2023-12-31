package com.kai.momentz.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PostResponse(

    @field:SerializedName("data")
    val data: List<ListPost?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class ListPost(
    @field:SerializedName("id_post")
    val idPost: Int? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("caption")
    val caption: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null

)