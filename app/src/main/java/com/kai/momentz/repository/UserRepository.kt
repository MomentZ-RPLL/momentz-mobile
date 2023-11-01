package com.kai.momentz.repository

import com.kai.momentz.data.UserPreference
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.retrofit.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


abstract class UserRepository(private val apiService: ApiService, private val pref: UserPreference) : Repository {

    override fun login(user: User) {
        GlobalScope.launch {
            pref.login(user)
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = apiService.registerUser(registerRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status != "200") {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("Registration failed"))
                }
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}