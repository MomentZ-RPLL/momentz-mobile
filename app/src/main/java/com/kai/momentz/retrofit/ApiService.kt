package com.kai.momentz.retrofit

import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/user/register")
    suspend fun registerUser(
        @Part profile_picture: MultipartBody.Part? = null,
        @Part("username") username: String,
        @Part("password") password: String,
        @Part("name") name: String,
        @Part("email") email: String,
        @Part("bio") bio: String? = null,
        @Part("created_at") createdAt: String? = null
    ) : Response<RegisterResponse>
}