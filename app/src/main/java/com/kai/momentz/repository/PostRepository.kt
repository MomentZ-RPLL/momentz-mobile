package com.kai.momentz.repository

import androidx.lifecycle.LiveData
import com.kai.momentz.data.UserPreference
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.model.response.UpdateProfileResponse
import com.kai.momentz.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostRepository(private val apiService: ApiService, private val pref: UserPreference) : Repository() {
    override fun login(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        username: RequestBody,
        password: RequestBody,
        name: RequestBody,
        email: RequestBody
    ): Result<RegisterResponse> {
        TODO("Not yet implemented")
    }


    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun getUser(): LiveData<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(token:String, username:String): Result<ProfileResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(
        token: String,
        username: String,
        profilePicture: MultipartBody.Part?,
        name: RequestBody?,
        email: RequestBody?,
        bio: RequestBody?,
        delPict: Boolean
    ): Result<UpdateProfileResponse> {
        TODO("Not yet implemented")
    }


    override suspend fun getFollowing(token: String, id: String): Result<FollowingResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowers(token: String, id: String): Result<FollowingResponse> {
        TODO("Not yet implemented")
    }
}