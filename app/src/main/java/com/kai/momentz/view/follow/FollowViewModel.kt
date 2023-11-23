package com.kai.momentz.view.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.FollowResponse
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class FollowViewModel(private val repository : Repository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowing = MutableLiveData<FollowingResponse>()
    val listFollowing: LiveData<FollowingResponse> = _listFollowing

    private val _listFollowers = MutableLiveData<FollowingResponse>()
    val listFollowers: LiveData<FollowingResponse> = _listFollowers

    private val _followResponse = MutableLiveData<FollowResponse>()
    val followResponse: LiveData<FollowResponse> = _followResponse

    fun getFollowing(token:String, id:String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getFollowing(token, id)
            _isLoading.value = false
            _listFollowing.value = result.getOrNull()
        }
    }

    fun getFollowers(token:String, id:String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getFollowers(token, id)
            _isLoading.value = false
            _listFollowers.value = result.getOrNull()
        }
    }

    fun followUser(
        token:String,
        id:String,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.followUser(token, id)
            _isLoading.value = false
            _followResponse.value = result.getOrNull()
        }
    }

    fun unfollowUser(
        token:String,
        id:String,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.unfollowUser(token, id)
            _isLoading.value = false
            _followResponse.value = result.getOrNull()
        }
    }

    fun getUser(): LiveData<User> {
        return repository.getUser()
    }



}