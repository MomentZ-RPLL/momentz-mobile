package com.kai.momentz.repository

import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.RegisterResponse

interface Repository {
    fun login(user: User)
    suspend fun register(registerRequest: RegisterRequest) : Result<RegisterResponse>
}