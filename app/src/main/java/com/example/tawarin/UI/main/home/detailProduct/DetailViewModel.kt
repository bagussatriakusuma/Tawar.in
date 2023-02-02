package com.example.tawarin.UI.main.home.detailProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderRequest
import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerOrder.GetBuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerProduct.ProductByIdResponse
import com.example.tawarin.Repository.AuthRepository
import com.example.tawarin.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repoAuth: AuthRepository, private val repo: ProductRepository): ViewModel() {
    val showProductDetails: MutableLiveData<ProductByIdResponse> = MutableLiveData()
    val showBuyerOrder: MutableLiveData<BuyerOrderResponse> = MutableLiveData()
    val showGetBuyerOrder: MutableLiveData<List<GetBuyerOrderResponse>> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    val showToken: MutableLiveData<Boolean> = MutableLiveData()
    val showShimerProduct: MutableLiveData<Boolean> = MutableLiveData()

    fun getToken(){
        viewModelScope.launch {
            repoAuth.getToken()
            withContext(Dispatchers.Main){
                showToken.postValue(true)
            }
        }
    }

    fun getProductBuyerById(id: Int){
        showShimerProduct.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getProductBuyerById(id)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showShimerProduct.postValue(false)
                    showProductDetails.postValue(data!!)
                }else{
                    //show error
                    val data = result.errorBody()
                    showShimerProduct.postValue(false)
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

    fun postBuyerOrder(requestBuyerOrderRequest: BuyerOrderRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.postBuyerOrder(token = repoAuth.getToken()!!, requestBuyerOrderRequest )
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showBuyerOrder.postValue(data!!)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }

}