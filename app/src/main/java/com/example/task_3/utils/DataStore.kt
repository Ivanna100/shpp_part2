package com.example.task_3.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object DataStoreManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.REGISTER_DATA_STORE)

    suspend fun getDataFromKey(context: Context, key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return if(preferences.contains(dataStoreKey)) {
            preferences[dataStoreKey]
        } else {
            null
        }
    }

    suspend fun putData(context: Context, key: String, value: String) {
        val dataStorekey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStorekey] = value
        }
    }

    suspend fun deleteDataFromDataStore(context: Context, key: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings.remove(dataStoreKey)
        }
    }
}