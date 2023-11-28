package com.kai.momentz.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.di.Injection
import com.kai.momentz.repository.Repository
import com.kai.momentz.view.chat.ChatViewModel
import com.kai.momentz.view.follow.FollowViewModel
import com.kai.momentz.view.home.HomeViewModel
import com.kai.momentz.view.login.LoginViewModel
import com.kai.momentz.view.notification.NotificationViewModel
import com.kai.momentz.view.post.PostViewModel
import com.kai.momentz.view.profile.ProfileViewModel
import com.kai.momentz.view.register.RegisterViewModel
import com.kai.momentz.view.search.SearchViewModel

class ViewModelFactory(private val repository: Repository)  : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FollowViewModel::class.java) -> {
                FollowViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> {
                NotificationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                ChatViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PostViewModel::class.java) -> {
                PostViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getUserInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideUserRepository(context))
            }.also { instance = it }
        }

//        fun getPostInstance(context: Context): ViewModelFactory {
//            return instance ?: synchronized(this) {
//                instance ?: ViewModelFactory(Injection.providePostRepository(context))
//            }.also { instance = it }
//        }

    }
}