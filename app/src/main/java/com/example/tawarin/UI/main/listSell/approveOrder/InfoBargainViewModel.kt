package com.example.tawarin.UI.main.listSell.approveOrder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tawarin.Data.api.main.seller.sellerApproveOrder.ApproveOrderRequest
import com.example.tawarin.Data.api.main.seller.sellerApproveOrder.ApproveOrderResponse
import com.example.tawarin.Repository.AuthRepository
import com.example.tawarin.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfoBargainViewModel @Inject constructor(private val repo: ProductRepository, private val repoAuth: AuthRepository): ViewModel() {
    val showApproveOrder: MutableLiveData<ApproveOrderResponse> = MutableLiveData()
    val showErrorDeclineOrder: MutableLiveData<String> = MutableLiveData()

    fun acceptDeclineOrder(id: Int, approveOrderRequest: ApproveOrderRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.approveOrder(token = repoAuth.getToken()!!, id, approveOrderRequest)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showApproveOrder.postValue(data!!)
                }else{
                    //show error
                    val data = result.errorBody()
                    showErrorDeclineOrder.postValue(data.toString())
                }
            }
        }
    }

}