package com.kai.momentz.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kai.momentz.data.UserPreference
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.CommentNotificationResponse
import com.kai.momentz.model.response.FollowResponse
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.model.response.LikeNotificationResponse
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.model.response.UpdateProfileResponse
import com.kai.momentz.retrofit.ApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserRepository(private val apiService: ApiService, private val pref: UserPreference) : Repository() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun login(user: User) {
        GlobalScope.launch {
            pref.login(user)
        }
    }

    override suspend fun register(username: RequestBody, password:RequestBody, name:RequestBody, email:RequestBody): Result<RegisterResponse> {
        return try {
            val response = apiService.registerUser(username=username, password=password, name=name, email=email)
            if (response.isSuccessful) {
                val responseBody = response.body()
                Result.success(responseBody!!)
            } else {
                Log.d("username", response.errorBody()?.string()!!)
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Log.d("username", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getProfile(token:String, username:String): Result<ProfileResponse> {
        return try {
            val response = apiService.getProfile("token=$token", username)
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

    override suspend fun updateProfile(
        token: String,
        username:String,
        profilePicture: MultipartBody.Part?,
        name:RequestBody?,
        email: RequestBody?,
        bio:RequestBody?,
        delPict: Boolean,
    ): Result<UpdateProfileResponse> {
        return try {
            val response = apiService.editProfile("token=$token", username, delPict,
                profilePicture, name, email, bio)
            if (response.isSuccessful) {
                val responseBody = response.body()
                Result.success(responseBody!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Log.d("Tes", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun getFollowing(token:String, id:String): Result<FollowingResponse> {
        return try {
            val response = apiService.getFollowing("token=$token", id)
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

    override suspend fun getFollowers(token:String, id:String): Result<FollowingResponse> {
        return try {
            val response = apiService.getFollowers("token=$token", id)
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

    override suspend fun followUser(token: String, id: String): Result<FollowResponse> {
        return try {
            val response = apiService.followUser("token=$token", id)
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

    override suspend fun unfollowUser(token: String, id: String): Result<FollowResponse> {
        return try {
            val response = apiService.unfollowUser("token=$token", id)
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

    override suspend fun likeNotification(token: String): Result<LikeNotificationResponse> {
        return try {
            val response = apiService.likeNotif("token=$token")
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

    override suspend fun commentNotification(token: String): Result<CommentNotificationResponse> {
        return try {
            val response = apiService.commentNotif("token=$token")
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

    override fun getToken(): String {
        return pref.getToken()
    }

    override fun logout() {
        GlobalScope.launch {
            pref.logout()
        }
    }
}