package com.kai.momentz.view.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.CommentResponse
import com.kai.momentz.model.response.TimelineResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: Repository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _commentResponse = MutableLiveData<CommentResponse>()
    val commentResponse: LiveData<CommentResponse> = _commentResponse

    fun getPost(
        token:String,
        id:String
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getDetailPost(token,id)
            _isLoading.value = false
            _commentResponse.value = result.getOrNull()
        }
    }

    fun postComment(
        token:String,
        comment:String
    ){

    }

    fun getUser(): LiveData<User>{
        return repository.getUser()
    }
}