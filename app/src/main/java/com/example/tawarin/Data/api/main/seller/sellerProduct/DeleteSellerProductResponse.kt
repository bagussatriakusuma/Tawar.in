package com.example.tawarin.Data.api.main.seller.sellerProduct

import com.google.gson.annotations.SerializedName

data class DeleteSellerProductResponse (

    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("message" ) var message : String? = null

)