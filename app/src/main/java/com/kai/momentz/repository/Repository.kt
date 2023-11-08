package com.kai.momentz.repository

import androidx.lifecycle.LiveData
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse

abstract class Repository {
    abstract fun login(user: User)
    abstract suspend fun register(username:String, password:String, name:String, email:String) : Result<RegisterResponse>
    abstract fun logout(user:User)
    abstract fun getUser(): LiveData<User>
    abstract fun getProfile(username:String): Result<ProfileResponse>
}