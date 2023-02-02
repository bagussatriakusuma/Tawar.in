package com.example.tawarin.UI.auth.register

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawarin.Data.api.ErrorResponse
import com.example.tawarin.Data.api.auth.login.LoginRequest
import com.example.tawarin.Data.api.auth.register.RegisterRequest
import com.example.tawarin.Data.local.UserEntity
import com.example.tawarin.Repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repo: AuthRepository): ViewModel() {
    private var full_name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var phone_number: String = ""
    private var address: String = ""
    private var city: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowErrorEmail: MutableLiveData<String> = MutableLiveData()
    val shouldShowErrorPassword: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenUpdateProfile: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenLoginPage: MutableLiveData<Boolean> = MutableLiveData()
    val showConfirmation: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeFullName(full_name: String) {
        this.full_name = full_name
    }

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onChangePhoneNumber(phone_number: String) {
        this.phone_number = phone_number
    }

    fun onChangeAddress(address: String) {
        this.address = address
    }

    fun onChangeCity(city: String) {
        this.city = city
    }

    fun onValidate() {
//        if (full_name.isEmpty() && full_name.length < 3) {
//            shouldShowError.postValue("Nama tidak boleh kosong")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowErrorEmail.postValue("Invalid email address")
        } else if (password.length < 8) {
            shouldShowErrorPassword.postValue("Your password is less than 8 characters")
//        } else if (phone_number.isEmpty() && password.length < 11) {
//            shouldShowError.postValue("Nomor telepon tidak boleh kosong")
//        } else if (address.isEmpty() && address.length < 3) {
//            shouldShowError.postValue("Alamat tidak boleh kosong")
//        } else if (city.isEmpty() && city.length < 3) {
//            shouldShowError.postValue("Kota tidak boleh kosong")
        } else {
            showConfirmation.postValue(true)
//            processToSignUp()
        }
    }

    fun processToSignUp() {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val request = RegisterRequest(
                fullName = full_name,
                email = email,
                password = password,
                phoneNumber = phone_number,
//                address = address,
//                city = city,
            )
            val result = repo.registerUser(request = request)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    processToSignIn()
                } else {
                    showErrorMessage(result.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

    fun processToSignIn() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = LoginRequest(
                email = email,
                password = password
            )
            val response = repo.loginUser(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    signInResponse?.let {
                        // mempersiapkan untuk simpan token
                        val token = it.accessToken.orEmpty()
                        insertToken(token = token)
//                        getUserData(token = token)

                        it.id
                        it.email
                    }
                    shouldOpenLoginPage.postValue(true)
                    shouldShowLoading.postValue(false)
                } else {
                    showErrorMessage(response.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

//    fun getUserData(token: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = repo.getUser(token = token)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    val getUserResponse = response.body()
//                    getUserResponse?.let {
//                        val userEntity = UserEntity(
//                            id = it.id.hashCode(),
//                            full_name = it.fullName.orEmpty(),
//                            email = it.email.orEmpty(),
////                            password = it.password.orEmpty(),
//                            phone_number = it.phoneNumber.orEmpty(),
//                            address = it.address.orEmpty(),
//                            image_url = it.imageUrl.orEmpty()
//                        )
//                        insertProfile(userEntity)
//                    }
//                } else {
//                    showErrorMessage(response.errorBody())
//                }
//            }
//        }
//    }

    fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            viewModelScope.launch {
                repo.updateToken(token)
            }
        }
    }

    fun insertProfile(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.insertUser(userEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldOpenLoginPage.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
                }
            }
        }
    }

    private fun showErrorMessage(response: ResponseBody?) {
        val error =
            Gson().fromJson(response?.string(), ErrorResponse::class.java)
        shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
    }
}