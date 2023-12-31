package com.kai.momentz.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.ErrorResponse
import com.kai.momentz.model.response.LikeResponse
import com.kai.momentz.model.response.TimelineResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _timelineResponse = MutableLiveData<TimelineResponse>()
    val timelineResponse : LiveData<TimelineResponse> = _timelineResponse

    private val _likeResponse = MutableLiveData<LikeResponse>()
    val likeResponse : LiveData<LikeResponse> = _likeResponse

    fun getTimeline(
        token:String,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getTimeline(token)
            _isLoading.value = false
            _timelineResponse.value = result.getOrNull()

        }
    }

    fun likePost(
        token:String,
        id:String
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.postLike(token, id)
            _isLoading.value = false
            _likeResponse.value = result.getOrNull()

        }
    }
    fun unlikePost(
        token:String,
        id:String
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.postUnlike(token, id)
            _isLoading.value = false
            _likeResponse.value = result.getOrNull()
        }
    }

    fun getUser(): LiveData<User>{
        return repository.getUser()
    }


}