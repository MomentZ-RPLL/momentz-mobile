package com.kai.momentz.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository : Repository) : ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

    fun getProfile(token:String, username:String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getProfile(token, username)
            _isLoading.value = false
            _profileResponse.value = result.getOrNull()
        }
    }

    fun getUser(): LiveData<User> {
        return repository.getUser()
    }

    fun logout() {
        return repository.logout()
    }
}