package com.kai.momentz.view.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.momentz.model.datastore.User
import com.kai.momentz.model.request.SendMessageRequest
import com.kai.momentz.model.response.ChatDetailResponse
import com.kai.momentz.model.response.ChatListResponse
import com.kai.momentz.model.response.SendChatResponse
import com.kai.momentz.repository.Repository
import kotlinx.coroutines.launch

class ChatViewModel(private val repository : Repository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _chatListResponse = MutableLiveData<ChatListResponse>()
    val chatListResponse: LiveData<ChatListResponse> = _chatListResponse

    private val _chatDetailResponse = MutableLiveData<ChatDetailResponse>()
    val chatDetailResponse: LiveData<ChatDetailResponse> = _chatDetailResponse

    private val _sendChatResponse = MutableLiveData<SendChatResponse>()
    val sendChatResponse: LiveData<SendChatResponse> = _sendChatResponse

    fun getChatList(token:String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getChatList(token)
            _isLoading.value = false
            _chatListResponse.value = result.getOrNull()
        }
    }

    fun getChatDetail(token:String, id: String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getChatDetail(token, id)
            _isLoading.value = false
            _chatDetailResponse.value = result.getOrNull()
        }
    }

    fun sendChat(token:String, id: String, message: SendMessageRequest){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.sendChat(token, id, message)
            _isLoading.value = false
            _sendChatResponse.value = result.getOrNull()
        }
    }

    fun getUser(): LiveData<User> {
        return repository.getUser()
    }
}