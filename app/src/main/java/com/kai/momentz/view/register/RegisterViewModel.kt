package com.kai.momentz.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun registerUser(username:String, password:String, name:String, email:String){

        viewModelScope.launch {
            _isLoading.value = true
            var result = repository.register(username, password, name, email)
            _isLoading.value = false

            _registerResponse.value = result.getOrNull()
        }
    }
}