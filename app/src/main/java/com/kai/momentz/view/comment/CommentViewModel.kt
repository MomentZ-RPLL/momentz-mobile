package com.kai.momentz.view.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.response.CommentResponse
import com.kai.momentz.model.response.PostCommentResponse
import com.kai.momentz.model.response.PostDetailResponse
import com.kai.momentz.model.response.TimelineResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: Repository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _postDetailResponse = MutableLiveData<PostDetailResponse>()
    val postDetailResponse: LiveData<PostDetailResponse> = _postDetailResponse

    private val _postCommentResponse = MutableLiveData<PostCommentResponse>()
    val postCommentResponse: LiveData<PostCommentResponse> = _postCommentResponse

    fun getDetailPost(
        token:String,
        id:String
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getDetailPost(token,id)
            _isLoading.value = false
            _postDetailResponse.value = result.getOrNull()
        }
    }

    fun postComment(
        token:String,
        id: String,
        comment:String
    ){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.postComment(token, id, comment)
            _isLoading.value = false
            _postCommentResponse.value = result.getOrNull()
        }
    }

    fun getUser(): LiveData<User>{
        return repository.getUser()
    }
}