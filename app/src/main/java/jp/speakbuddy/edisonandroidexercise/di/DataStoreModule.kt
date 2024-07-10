package jp.speakbuddy.edisonandroidexercise.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideLastFactKey(): Preferences.Key<String> = stringPreferencesKey("last_fact")
}

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val lastFactKey: Preferences.Key<String>
) {
    val lastFact: Flow<String?> = dataStore.data.map { preferences ->
        preferences[lastFactKey]
    }

    suspend fun saveLastFact(fact: String) {
        dataStore.edit { preferences ->
            preferences[lastFactKey] = fact
        }
    }
}
