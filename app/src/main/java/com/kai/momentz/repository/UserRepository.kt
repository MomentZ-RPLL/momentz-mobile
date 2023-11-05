package com.kai.momentz.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kai.momentz.data.UserPreference
import com.kai.momentz.di.dataStore
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.retrofit.ApiConfig
import com.kai.momentz.retrofit.ApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class UserRepository(private val apiService: ApiService, private val pref: UserPreference) : Repository() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun login(user: User) {
        GlobalScope.launch {
            pref.login(user)
        }
    }

    override suspend fun register(username:String, password:String, name:String, email:String): Result<RegisterResponse> {
        return try {
            val response = apiService.registerUser(username=username, password=password, name=name, email=email)
            if (response.isSuccessful) {
                val responseBody = response.body()
                Result.success(responseBody!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    override fun logout(user: User) {
        TODO("Not yet implemented")
    }
}