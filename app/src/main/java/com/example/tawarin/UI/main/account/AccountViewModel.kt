package com.example.tawarin.UI.main.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawarin.Data.api.auth.getUser.GetUserResponse
import com.example.tawarin.Data.api.main.buyer.buyerOrder.GetBuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerOrder.Product
import com.example.tawarin.Repository.AuthRepository
import com.example.tawarin.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val repo: ProductRepository, private val repoAuth: AuthRepository): ViewModel() {
    val showLogin: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    val showUser: MutableLiveData<GetUserResponse> = MutableLiveData()
    val showGetBuyerOrder: MutableLiveData<List<GetBuyerOrderResponse>> = MutableLiveData()

    fun clearCredential(){
        viewModelScope.launch {
            repoAuth.clearToken()
            withContext(Dispatchers.Main){
                showLogin.postValue(true)
            }
        }
    }

    fun getUserData(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repoAuth.getUser(token = repoAuth.getToken()!!)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showUser.postValue(data!!)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }

    fun getBuyerOrder(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getBuyerOrder(token = repoAuth.getToken()!!)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showGetBuyerOrder.postValue(data!!)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }
}