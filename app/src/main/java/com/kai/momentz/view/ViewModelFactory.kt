package com.kai.momentz.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kai.momentz.repository.Repository
import com.kai.momentz.view.login.LoginViewModel
import com.kai.momentz.view.register.RegisterViewModel

class ViewModelFactory(private val repository: Repository)  : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
//            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
//                LoginViewModel(repo) as T
//            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(repository: Repository): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(repository)
            }.also { instance = it }
        }
    }
}