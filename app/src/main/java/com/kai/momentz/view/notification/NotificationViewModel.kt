package com.kai.momentz.view.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.CommentNotificationResponse
import com.kai.momentz.model.response.FollowNotificationResponse
import com.kai.momentz.model.response.FollowResponse
import com.kai.momentz.model.response.FollowingResponse
import com.kai.momentz.model.response.LikeNotificationResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository : Repository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _likeNotificationResponse = MutableLiveData<LikeNotificationResponse>()
    val likeNotificationResponse: LiveData<LikeNotificationResponse> = _likeNotificationResponse

    private val _commentNotificationResponse = MutableLiveData<CommentNotificationResponse>()
    val commentNotificationResponse: LiveData<CommentNotificationResponse> = _commentNotificationResponse

    private val _followNotificationResponse = MutableLiveData<FollowNotificationResponse>()
    val followNotificationResponse: LiveData<FollowNotificationResponse> = _followNotificationResponse

    private val _listFollowing = MutableLiveData<FollowingResponse>()
    val listFollowing: LiveData<FollowingResponse> = _listFollowing

    private val _followResponse = MutableLiveData<FollowResponse>()
    val followResponse: LiveData<FollowResponse> = _followResponse

    fun likeNotification(
        token:String,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.likeNotification(token)
            _isLoading.value = false
            _likeNotificationResponse.value = result.getOrNull()
        }
    }

    fun commentNotification(
        token:String,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.commentNotification(token)
            _isLoading.value = false
            _commentNotificationResponse.value = result.getOrNull()
        }
    }

    fun getFollowing(token:String, id:String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getFollowing(token, id)
            _isLoading.value = false
            _listFollowing.value = result.getOrNull()
        }
    }

    fun followNotification(
        token:String,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.followNotification(token)
            _isLoading.value = false
            _followNotificationResponse.value = result.getOrNull()
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