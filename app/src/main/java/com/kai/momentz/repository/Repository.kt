package com.kai.momentz.repository

import androidx.lifecycle.LiveData
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.request.UpdateProfileRequest
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.model.response.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

abstract class Repository {
    abstract fun login(user: User)
    abstract suspend fun register(username: RequestBody, password:RequestBody, name:RequestBody, email:RequestBody) : Result<RegisterResponse>
    abstract fun logout()

    abstract fun getUser(): LiveData<User>
    abstract suspend fun getProfile(token:String, username:String): Result<ProfileResponse>

    abstract suspend fun updateProfile(token:String,
                                       username:String,
                                       profilePicture:MultipartBody.Part?,
                                       name:RequestBody?,
                                       email: RequestBody?,
                                       bio:RequestBody? ): Result<UpdateProfileResponse>

    abstract suspend fun getFollowing(token:String, id:String): Result<FollowingResponse>

    abstract suspend fun getFollowers(token:String, id:String): Result<FollowingResponse>



}