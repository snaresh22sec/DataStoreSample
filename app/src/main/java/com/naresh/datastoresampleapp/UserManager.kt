package com.naresh.datastoresampleapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")

class UserManager(context: Context) {

    private val mDataStore: DataStore<Preferences> = context.dataStore

    companion object{
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
    }

    suspend fun storeUserData(age:Int, name:String){
        mDataStore.edit { preferences->
            preferences[USER_AGE_KEY] = age
            preferences[USER_NAME_KEY] = name

        }
    }

    val userAgeFlow: Flow<Int> = mDataStore.data.map {
        it[USER_AGE_KEY] ?: 0
    }

    val userNameFlow: Flow<String> = mDataStore.data.map {
        it[USER_NAME_KEY] ?: "empty"
    }
}