package com.example.tawarin.Repository

import com.example.tawarin.Data.api.auth.AuthAPI
import com.example.tawarin.Data.api.auth.editProfile.UpdateUserResponse
import com.example.tawarin.Data.api.auth.getUser.GetUserResponse
import com.example.tawarin.Data.api.auth.login.LoginRequest
import com.example.tawarin.Data.api.auth.login.LoginResponse
import com.example.tawarin.Data.api.auth.register.RegisterRequest
import com.example.tawarin.Data.api.auth.register.RegisterResponse
import com.example.tawarin.Data.local.UserDAO
import com.example.tawarin.Data.local.UserEntity
import com.example.tawarin.Datastore.AuthDataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataStore: AuthDataStoreManager,
    private val api: AuthAPI,
    private val dao: UserDAO
) {
    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return api.postRegister(request)
    }

    suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return api.postLogin(request)
    }

    suspend fun getUser(token: String): Response<GetUserResponse> {
        return api.getUser(token = "Bearer $token")
    }

    suspend fun updateUser(
        token: String,
        image: MultipartBody.Part?,
        name: RequestBody?,
        phoneNumber: RequestBody?,
        address: RequestBody?,
        city: RequestBody?
    ): Response<UpdateUserResponse> {
        return api.updateUser("Bearer $token", image, name, phoneNumber, address, city)
    }

    suspend fun clearToken() {
        return updateToken("")
    }

    suspend fun updateToken(value: String) {
        authDataStore.setToken(value)
    }

    suspend fun getToken(): String? {
        return authDataStore.getToken().firstOrNull()
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }
}