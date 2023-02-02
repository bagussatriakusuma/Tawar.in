package com.example.tawarin.DI

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.tawarin.Data.local.UserDAO
import com.example.tawarin.Database.UserDatabase
import com.example.tawarin.Datastore.AuthDataStoreManager
import com.example.tawarin.Utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return UserDatabase.getInstance(context = context)
    }

    @Singleton
    @Provides
    fun provideUserDao(db: UserDatabase): UserDAO {
        return db.userDAO()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            Constant.Pref_Name,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideAuthDataStoreManager(@ApplicationContext context: Context): AuthDataStoreManager {
        return AuthDataStoreManager(context = context)
    }
}