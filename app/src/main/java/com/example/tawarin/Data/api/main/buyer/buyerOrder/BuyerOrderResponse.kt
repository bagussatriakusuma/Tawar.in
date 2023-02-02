package com.example.tawarin.Data.api.main.buyer.buyerOrder

import com.google.gson.annotations.SerializedName

data class BuyerOrderResponse (

    @SerializedName("message" ) var message : String? = null,
    @SerializedName("order"   ) var order   : Order?  = Order()

) {
    data class Order (

        @SerializedName("id"               ) var id              : Int?              = null,
        @SerializedName("buyer_id"         ) var buyerId         : Int?              = null,
        @SerializedName("seller_id"        ) var sellerId        : Int?              = null,
        @SerializedName("product_id"       ) var productId       : Int?              = null,
        @SerializedName("price"            ) var price           : Int?              = null,
        @SerializedName("transaction_date" ) var transactionDate : String?           = null,
        @SerializedName("product_name"     ) var productName     : String?           = null,
        @SerializedName("base_price"       ) var basePrice       : Int?              = null,
        @SerializedName("image_url"        ) var imageUrl        : ArrayList<String> = arrayListOf(),
        @SerializedName("status"           ) var status          : String?           = null,
        @SerializedName("updatedAt"        ) var updatedAt       : String?           = null,
        @SerializedName("createdAt"        ) var createdAt       : String?           = null

    )
}