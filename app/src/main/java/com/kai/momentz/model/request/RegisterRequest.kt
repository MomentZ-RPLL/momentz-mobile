package com.kai.momentz.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest (

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("message")
    var password: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("email")
    var email: String,

    @field:SerializedName("email")
    var bio: String? = null,
): Parcelable