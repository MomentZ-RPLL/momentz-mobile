package com.kai.momentz.view.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.LoginRequest
import com.kai.momentz.model.response.ErrorResponse
import com.kai.momentz.model.response.LoginResponse
import com.kai.momentz.repository.Repository
import com.kai.momentz.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginUser(username: String, password: String){
        _isLoading.value = true
        val loginRequest = LoginRequest(username, password)
        val client = ApiConfig().getApiService().loginUser(loginRequest)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loginResponse.value = response.body()
                    }
                } else {
                    val errBody = response.errorBody()
                    val errJsonString = errBody?.string()
                    val gson = Gson()
                    _errorResponse.value = gson.fromJson(errJsonString, ErrorResponse::class.java)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
                _errorResponse.value = ErrorResponse(null, null)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun login(user: User) {
        return repository.login(user)
    }

    fun getUser(): LiveData<User> {
        return repository.getUser()
    }

    fun getIsFirst(): Boolean {
        return repository.getIsFirst()
    }

    fun setIsFirst(isFirst: Boolean){
        return repository.setIsFirst(isFirst)
    }
}