package com.example.tawarin.Data.api.main.notification

import com.google.gson.annotations.SerializedName

data class NotificationResponse (

    @SerializedName("id"                ) var id               : Int?              = null,
    @SerializedName("product_id"        ) var productId        : Int?              = null,
    @SerializedName("product_name"      ) var productName      : String?           = null,
    @SerializedName("bid_price"         ) var bidPrice         : Int?              = null,
    @SerializedName("transaction_date"  ) var transactionDate  : String?           = null,
    @SerializedName("status"            ) var status           : String?           = null,
    @SerializedName("notification_type" ) var notificationType : String?           = null,
    @SerializedName("order_id"          ) var orderId          : Int?              = null,
    @SerializedName("seller_name"       ) var sellerName       : String?           = null,
    @SerializedName("buyer_name"        ) var buyerName        : String?           = null,
    @SerializedName("receiver_id"       ) var receiverId       : Int?              = null,
    @SerializedName("image_url"         ) var imageUrl         : ArrayList<String> = arrayListOf(),
    @SerializedName("read"              ) var read             : Boolean?          = null,
    @SerializedName("createdAt"         ) var createdAt        : String?           = null,
    @SerializedName("updatedAt"         ) var updatedAt        : String?           = null

)
