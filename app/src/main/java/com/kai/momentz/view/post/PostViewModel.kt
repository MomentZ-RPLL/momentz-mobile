package com.kai.momentz.view.post

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.ErrorResponse
import com.kai.momentz.model.response.PostResponse
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.repository.Repository
import com.kai.momentz.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostViewModel(private val repository : Repository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _postStoryResponse = MutableLiveData<PostResponse>()
    val postResponse: LiveData<PostResponse> = _postStoryResponse

    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    fun createPost(token:String, imageMultipart: MultipartBody.Part, caption : RequestBody, lat:Double?, lon:Double? ){
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val postImage = apiService.createPost("Bearer $token", imageMultipart, caption, lat, lon)

        postImage.enqueue(object : Callback<ErrorResponse> {
            override fun onResponse(
                call: Call<ErrorResponse>,
                response: Response<ErrorResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _errorResponse.value = response.body()
                } else {
                    val error = response.errorBody()
                    val errorJsonString = error?.string()
                    val gson = Gson()
                    _errorResponse.value = gson.fromJson(errorJsonString, ErrorResponse::class.java)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getUser(): LiveData<User> {
        return repository.getUser()
    }
}