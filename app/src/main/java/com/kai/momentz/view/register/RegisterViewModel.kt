package com.kai.momentz.view.register

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.LoginRequest
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.ErrorResponse
import com.kai.momentz.model.response.LoginResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.model.response.UpdateProfileResponse
import com.kai.momentz.repository.Repository
import com.kai.momentz.retrofit.ApiConfig
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val repository: Repository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    val updateProfileResponse: LiveData<UpdateProfileResponse> = _updateProfileResponse

    fun registerUser(username: RequestBody, password:RequestBody, name:RequestBody, email:RequestBody){

        viewModelScope.launch {

            _isLoading.value = true
            val result = repository.register(username, password, name, email)
            _isLoading.value = false

            _registerResponse.value = result.getOrNull()
        }
    }

    fun editProfile(
        token:String,

        username:String,
        profilePicture: MultipartBody.Part?,
        name: RequestBody,
        email: RequestBody,
        bio: RequestBody,
        delPict: Boolean,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.updateProfile(token, username, profilePicture, name, email, bio, delPict)
            _isLoading.value = false
            _updateProfileResponse.value = result.getOrNull()
        }
    }


}