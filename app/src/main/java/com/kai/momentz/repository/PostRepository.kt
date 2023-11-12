package com.kai.momentz.repository

import androidx.lifecycle.LiveData
import com.kai.momentz.data.UserPreference
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.retrofit.ApiService
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


    override fun logout(user: User) {
        TODO("Not yet implemented")
    }

    override fun getUser(): LiveData<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(token:String, username:String): Result<ProfileResponse> {
        TODO("Not yet implemented")
    }
}