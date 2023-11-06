package com.kai.momentz.retrofit

import com.kai.momentz.model.request.LoginRequest
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.PostResponse
import com.kai.momentz.model.response.LoginResponse
import com.kai.momentz.model.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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

    @POST()
    suspend fun getUsers(
        @Header("Authorization") token : String
    )
    
    @GET()
    suspend fun getPost(
        @Header("Authorization")token:String,
        @Query("id") ids:List<String>
    ): Call<PostResponse>
    @POST("/user/login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}