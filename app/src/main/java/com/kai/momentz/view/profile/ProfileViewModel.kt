package com.kai.momentz.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kai.momentz.model.response.ProfileResponse
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.repository.Repository

class ProfileViewModel(private val repo : Repository) : ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registerResponse = MutableLiveData<ProfileResponse>()
    val registerResponse: LiveData<ProfileResponse> = _registerResponse
}