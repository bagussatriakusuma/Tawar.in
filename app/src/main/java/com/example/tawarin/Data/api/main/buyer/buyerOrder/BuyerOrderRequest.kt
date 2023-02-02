package com.example.tawarin.Data.api.main.buyer.buyerOrder

import com.google.gson.annotations.SerializedName

data class BuyerOrderRequest (

    @SerializedName("product_id" ) var productId : String? = null,
    @SerializedName("price"      ) var price     : String? = null

)