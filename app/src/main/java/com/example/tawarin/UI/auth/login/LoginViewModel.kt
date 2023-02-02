package com.example.tawarin.UI.auth.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawarin.Data.api.ErrorResponse
import com.example.tawarin.Data.api.auth.login.LoginRequest
import com.example.tawarin.Data.local.UserEntity
import com.example.tawarin.Repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: AuthRepository): ViewModel() {
    private var email: String = ""
    private var password: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowErrorEmail: MutableLiveData<String> = MutableLiveData()
    val shouldShowErrorPassword: MutableLiveData<String> = MutableLiveData()
    val shouldOpenHomePage: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onClickSignIn() {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowErrorEmail.postValue("Invalid email address")
        } else if (password.length < 8) {
            shouldShowErrorPassword.postValue("Your password is less than 8 characters")
        } else {
            LoginAPI()
        }
    }

    fun LoginAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = LoginRequest(
                email = email,
                password = password
            )
            val response = repo.loginUser(request)
            showLoading.postValue(true)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        // mempersiapkan untuk simpan token
                        val token = it.accessToken.orEmpty()
                        insertToken(token = token)
                        getUserData(token = token)
                    }
                    showLoading.postValue(false)
                } else {
                    showLoading.postValue(false)
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
        }
    }

    private fun getUserData(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repo.getUser(token = token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getUserResponse = response.body()
                    getUserResponse?.let {
                        val userEntity = UserEntity(
                            id = it.id.hashCode(),
                            full_name = it.fullName.orEmpty(),
                            email = it.email.orEmpty(),
//                            password = it.password.orEmpty(),
                            phone_number = it.phoneNumber.orEmpty(),
                            address = it.address.orEmpty(),
                            image_url = it.imageUrl.orEmpty()
                        )
                        insertProfile(userEntity)
                    }
                    showLoading.postValue(false)
                } else {
                    showLoading.postValue(false)
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
        }
    }

    private fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            viewModelScope.launch {
                repo.updateToken(token)
            }
        }
    }

    private fun insertProfile(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.insertUser(userEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldOpenHomePage.postValue(true)
                } else {
                    showLoading.postValue(false)
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
                }
            }
        }
    }
}