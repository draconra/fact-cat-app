package jp.speakbuddy.edisonandroidexercise.corenetwork.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fact_data")

class DataStoreRepository(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val LAST_FACT_KEY = stringPreferencesKey("last_fact")
        private val FACT_HISTORY_KEY = stringSetPreferencesKey("fact_history")
    }

    val lastFact: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[LAST_FACT_KEY]
        }

    val factHistory: Flow<List<String>> = dataStore.data
        .map { preferences ->
            preferences[FACT_HISTORY_KEY]?.toList() ?: emptyList()
        }

    suspend fun saveLastFact(fact: String) {
        dataStore.edit { preferences ->
            preferences[LAST_FACT_KEY] = fact
        }
    }

    suspend fun addFactToHistory(fact: String) {
        dataStore.edit { preferences ->
            val currentHistory = preferences[FACT_HISTORY_KEY] ?: emptySet()
            preferences[FACT_HISTORY_KEY] = currentHistory + fact
        }
    }
}