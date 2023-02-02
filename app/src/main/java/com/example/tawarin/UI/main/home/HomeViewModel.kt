package com.example.tawarin.UI.main.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawarin.Data.api.auth.getUser.GetUserResponse
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.Data.api.main.buyer.buyerProduct.ProductResponse
import com.example.tawarin.Repository.AuthRepository
import com.example.tawarin.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: ProductRepository, private val repoAuth: AuthRepository): ViewModel() {
    val showLogin: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    val showUser: MutableLiveData<GetUserResponse> = MutableLiveData()
    val showCategory: MutableLiveData<CategoryResponse> = MutableLiveData()
    val showProductBuyer: MutableLiveData<ProductResponse> = MutableLiveData()

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

    @SuppressLint("NullSafeMutableLiveData")
    fun getCategory(){
        CoroutineScope(Dispatchers.IO).launch {
//            showShimmerCategory.postValue(true)
            val result = repo.getCategory()
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
//                    showShimmerCategory.postValue(false)
                    showCategory.postValue(data)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
//                    showShimmerCategory.postValue(false)
                }
            }
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun getProductBuyer(status: String, categoryId: String, search: String){
        CoroutineScope(Dispatchers.IO).launch {
//            showEmpty.postValue(false)
//            showShimmerProduct.postValue(true)
//            showRv.postValue(false)
            val result = repo.getProductBuyer(status, categoryId, search)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
//                    showShimmerProduct.postValue(false)
//                    if(data!!.isEmpty()){
//                        showEmpty.postValue(true)
//                    }
                    showProductBuyer.postValue(data)
//                    showRv.postValue(true)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
//                    showShimmerProduct.postValue(false)
//                    showEmpty.postValue(false)
//                    showRv.postValue(false)
                }
            }
        }
    }
}