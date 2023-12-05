package com.kai.momentz.retrofit

import com.kai.momentz.model.request.LoginRequest
import com.kai.momentz.model.request.SendMessageRequest
import com.kai.momentz.model.response.ChatDetailResponse
import com.kai.momentz.model.response.ChatListResponse
import com.kai.momentz.model.response.CommentNotificationResponse
import com.kai.momentz.model.response.CommentResponse
import com.kai.momentz.model.response.ErrorResponse
import com.kai.momentz.model.response.FollowNotificationResponse
import com.kai.momentz.model.response.FollowResponse
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.model.response.LikeNotificationResponse
import com.kai.momentz.model.response.LikeResponse
import com.kai.momentz.model.response.LoginResponse
import com.kai.momentz.model.response.PostDetailResponse
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.model.response.SearchUserResponse
import com.kai.momentz.model.response.TimelineResponse
import com.kai.momentz.model.response.SendChatResponse
import com.kai.momentz.model.response.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @GET("/timeline/")
    suspend fun getTimeline(
        @Header("Cookie") token: String,
    ): Response<TimelineResponse>

    @POST("/users/login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("/users/profile/{username}")
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
    @PUT("/users/profile/{username}")
    suspend fun editProfile(
        @Header("Cookie") token: String,
        @Path("username") username: String,
        @Query("del_pict") del_pict: Boolean,
        @Part profile_picture: MultipartBody.Part?,
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("bio") bio: RequestBody?,
    ): Response<UpdateProfileResponse>

    @POST("/users/follow/{id}")
    suspend fun followUser(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): Response<FollowResponse>

    @DELETE("/users/follow/{id}")
    suspend fun unfollowUser(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): Response<FollowResponse>

    @GET("/notification/likes")
    suspend fun likeNotification(
        @Header("Cookie") token: String,
    ): Response<LikeNotificationResponse>

    @GET("/notification/comments")
    suspend fun commentNotification(
        @Header("Cookie") token: String,
    ): Response<CommentNotificationResponse>

    @GET("/notification/follow")
    suspend fun followNotification(
        @Header("Cookie") token: String,
    ): Response<FollowNotificationResponse>

    @GET("/users/search")
    suspend fun searchUser(
        @Header("Cookie") token: String,
        @Query("username") username: String,
    ): Response<SearchUserResponse>

    @GET("/chats")
    suspend fun getChat(
        @Header("Cookie") token: String,
    ): Response<ChatListResponse>

    @GET("/chats/{id}")
    suspend fun getChatDetail(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): Response<ChatDetailResponse>

    @POST("/chats/{id}")
    suspend fun sendChat(
        @Header("Cookie") token: String,
        @Path("id") id: String,
        @Body message: SendMessageRequest
    ): Response<SendChatResponse>
    @Multipart
    @POST("/posts")
    fun createPost(
        @Header("Cookie") token: String,
        @Part post_media: MultipartBody.Part,
        @Part("caption") description: RequestBody,
        @Part("lat") lat : Double?,
        @Part("lon") lon : Double?,
    ): Call<ErrorResponse>

    @POST("/post/1/comments")
    fun createComment(
        @Header("Cookie") token: String,
        @Path("id") id: String,
    )

    @DELETE("/users/{id}/likes")
    suspend fun postUnlike(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): Response<LikeResponse>

    @POST("/posts/{id}")
    suspend fun postLike(
        @Header("Cookie") token: String,
        @Path("id") id: String
    ): Response<LikeResponse>

    @GET("/posts/{postId}")
    suspend fun getPostDetail(
        @Header("Cookie") token: String,
        @Path("postId") id: String
    ): Response<PostDetailResponse>
}