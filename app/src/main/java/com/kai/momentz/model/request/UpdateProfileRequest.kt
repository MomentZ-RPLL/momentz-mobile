package com.kai.momentz.model.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class UpdateProfileRequest(
    @Part("name") var name: RequestBody,
    @Part("email") var email: RequestBody,
    @Part("bio") var bio: RequestBody? = null,
    @Part var profile_picture: MultipartBody.Part? = null,
)
