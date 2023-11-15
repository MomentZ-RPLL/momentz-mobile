package com.kai.momentz.retrofit

import com.kai.momentz.model.request.LoginRequest
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.model.response.LoginResponse
import com.kai.momentz.model.response.PostResponse
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.model.response.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Multipart
    @POST("/users/register")
    suspend fun registerUser(
        @Part profile_picture: MultipartBody.Part? = null,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("bio") bio: RequestBody? = null,
        @Part("created_at") createdAt: RequestBody? = null
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

    @POST("/users/login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("/users/{username}")
    suspend fun getProfile(
        @Header("Cookie") token: String,
        @Path("username") username: String
    ): Response<ProfileResponse>

    @GET("/users/{id}/following")
    suspend fun getFollowing(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): Response<FollowingResponse>

    @GET("/users/{id}/followers")
    suspend fun getFollowers(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): Response<FollowingResponse>
    @Multipart
    @PUT("/users/{username}")
    suspend fun editProfile(
        @Header("Cookie") token: String,
        @Path("username") username: String,
        @Part profile_picture: MultipartBody.Part?,
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("bio") bio: RequestBody?,
    ): Response<UpdateProfileResponse>
}