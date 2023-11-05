package com.kai.momentz.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kai.momentz.model.datastore.User
import com.kai.momentz.repository.Repository

class HomeViewModel(private val repo : Repository) : ViewModel() {

    fun getUser(): LiveData<User> {
        return repo.getUser()
    }
}