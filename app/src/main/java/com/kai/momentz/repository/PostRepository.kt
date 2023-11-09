package com.kai.momentz.repository

import androidx.lifecycle.LiveData
import com.kai.momentz.data.UserPreference
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.retrofit.ApiService

class PostRepository(private val apiService: ApiService, private val pref: UserPreference) : Repository() {
    override fun login(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        username: String,
        password: String,
        name: String,
        email: String
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