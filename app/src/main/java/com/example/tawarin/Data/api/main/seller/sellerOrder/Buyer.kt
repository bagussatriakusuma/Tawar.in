package com.example.tawarin.Data.api.main.seller.sellerOrder

import com.google.gson.annotations.SerializedName

data class Buyer (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("full_name"    ) var fullName    : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("city"         ) var city        : String? = null,
    @SerializedName("address"      ) var address     : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : String? = null

)