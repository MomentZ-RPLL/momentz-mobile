package com.kai.momentz.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.UpdateProfileResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val repository : Repository) : ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

    private val _updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    val updateProfileResponse: LiveData<UpdateProfileResponse> = _updateProfileResponse

    fun getProfile(token:String, username:String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getProfile(token, username)
            _isLoading.value = false
            _profileResponse.value = result.getOrNull()
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

    fun getUser(): LiveData<User> {
        return repository.getUser()
    }

    fun logout() {
        return repository.logout()
    }
}