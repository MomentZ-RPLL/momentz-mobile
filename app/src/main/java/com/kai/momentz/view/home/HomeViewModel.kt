package com.kai.momentz.view.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kai.momentz.data.UserPreference
import com.kai.momentz.model.response.Data
import com.kai.momentz.model.response.ErrorResponse
import com.kai.momentz.model.response.PostResponse
import com.kai.momentz.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: UserPreference): ViewModel(){
    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    private val _listPost = MutableLiveData<PostResponse>()
    val listPost : LiveData<PostResponse> = _listPost

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object{
        const val TAG = "MainViewModel"
    }

//    suspend fun getProfileUser(token : String){
//        val client = ApiConfig().getApiService().getUsers("Bearer $token")
//        client.enqueue(object : Callback<UserResponse> {
//            override fun onResponse(
//                call: Call<UserResponse>,
//                response: Response<UserResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        _profileUser.value = response.body()
//                    }
//                } else {
//                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
//                }
//            }
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//
//                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    suspend fun getPost(id: List<String>, token:String){
        _isLoading.value = true
        val client = ApiConfig().getApiService().getPost("Bearer$token",id)
        client.enqueue(object : Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ){
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listPost.value = response.body()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(HomeViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


}