package com.example.tawarin.Data.api.main.buyer.buyerOrder

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("full_name"    ) var fullName    : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("password"     ) var password    : String? = null,
    @SerializedName("city"         ) var city        : String? = null,
    @SerializedName("address"      ) var address     : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : String? = null,
    @SerializedName("foto"         ) var foto        : String? = null,
    @SerializedName("createdAt"    ) var createdAt   : String? = null,
    @SerializedName("updatedAt"    ) var updatedAt   : String? = null

)