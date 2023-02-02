package com.example.tawarin.DI

import com.example.tawarin.Repository.AuthRepository
import com.example.tawarin.Data.api.auth.AuthAPI
import com.example.tawarin.Data.local.UserDAO
import com.example.tawarin.Datastore.AuthDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        authDataStoreManager: AuthDataStoreManager,
        api: AuthAPI,
        dao: UserDAO
    ): AuthRepository {
        return AuthRepository(
            authDataStore = authDataStoreManager,
            api = api,
            dao = dao
        )
    }
}