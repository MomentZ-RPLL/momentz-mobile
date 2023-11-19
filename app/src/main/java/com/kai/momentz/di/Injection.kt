package com.kai.momentz.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kai.momentz.data.UserPreference
import com.kai.momentz.repository.Repository
import com.kai.momentz.repository.UserRepository
import com.kai.momentz.retrofit.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("users")
object Injection {
    fun provideUserRepository(context: Context): Repository {
        val dataStore = context.dataStore
        val pref = UserPreference.getInstance(dataStore)
        val apiService = ApiConfig().getApiService()
        return UserRepository(apiService, pref)
    }

//    fun providePostRepository(context: Context): Repository {
//        val dataStore = context.dataStore
//        val pref = UserPreference.getInstance(dataStore)
//        val apiService = ApiConfig().getApiService()
//        return PostRepository(apiService, pref)
//    }
}