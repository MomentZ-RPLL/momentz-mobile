package com.kai.momentz.view.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.response.CommentNotificationResponse
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

    fun getToken(): String {
        return repository.getToken()
    }

}