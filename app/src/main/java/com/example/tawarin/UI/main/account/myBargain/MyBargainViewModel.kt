package com.example.tawarin.UI.main.account.myBargain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerOrder.GetBuyerOrderResponse
import com.example.tawarin.Repository.AuthRepository
import com.example.tawarin.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyBargainViewModel @Inject constructor(private val repoAuth: AuthRepository, private val repo: ProductRepository): ViewModel() {
    val showGetBuyerOrder: MutableLiveData<List<GetBuyerOrderResponse>> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()

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