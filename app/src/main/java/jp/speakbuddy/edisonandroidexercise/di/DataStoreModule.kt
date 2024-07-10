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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideLastFactKey(): Preferences.Key<String> = stringPreferencesKey("last_fact")

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>, lastFactKey: Preferences.Key<String>): DataStoreRepository {
        return DataStoreRepository(dataStore, lastFactKey)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
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
