package com.kai.momentz.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.response.RegisterResponse
import com.kai.momentz.model.response.SearchUserResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchUserResponse = MutableLiveData<SearchUserResponse>()
    val searchUserResponse: LiveData<SearchUserResponse> = _searchUserResponse

    fun searchUser(token: String, username: String){

        viewModelScope.launch {

            _isLoading.value = true
            val result = repository.searchUser(token, username)
            _isLoading.value = false

            _searchUserResponse.value = result.getOrNull()
        }
    }

    fun getToken(): String {
        return repository.getToken()
    }

}