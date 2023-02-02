package com.example.tawarin.Data.api.main.seller.sellerApproveOrder

import com.google.gson.annotations.SerializedName

data class ApproveOrderRequest(
    @SerializedName("status" ) var status : String
)
