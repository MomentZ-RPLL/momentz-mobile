package com.kai.momentz.model.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part

data class RegisterRequest (

    @Part("username") var username: String,
    @Part("password") var password: String,
    @Part("name") var name: String,
    @Part("email") var email: String,
    @Part("bio") var bio: String? = null,
    @Part("created_at") var createdAt: String? = null
)