package com.example.tawarin.Data.api.main.seller.sellerApproveOrder

import com.google.gson.annotations.SerializedName

data class ApproveOrderResponse (

    @SerializedName("message" ) var message : String?        = null,
    @SerializedName("order"   ) var order   : ArrayList<Int> = arrayListOf()

)