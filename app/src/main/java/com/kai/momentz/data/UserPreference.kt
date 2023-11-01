package com.kai.momentz.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kai.momentz.model.datastore.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

class UserPreference (private val dataStore : DataStore<Preferences>){

    fun getToken() : String {
        val preferences = runBlocking{ dataStore.data.first() }
        return preferences[TOKEN_KEY] ?: ""
    }

    fun getUser(): Flow<User> {
        return dataStore.data.map{ preferences ->
            User(
                preferences[ID_KEY] ?:"",
                preferences[NAME_KEY] ?:"",
                preferences[TOKEN_KEY] ?:"",
            )
        }
    }

    suspend fun login(user: User) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.username
            preferences[ID_KEY] = user.id
            preferences[TOKEN_KEY] = user.token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[ID_KEY] = ""
            preferences[TOKEN_KEY] = ""
        }
    }

    companion object {
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val ID_KEY = stringPreferencesKey("id")
        private val TOKEN_KEY = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<androidx.datastore.preferences.core.Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}