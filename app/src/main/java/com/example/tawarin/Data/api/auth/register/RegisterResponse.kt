package com.example.tawarin.Data.api.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("full_name"    ) var fullName    : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("password"     ) var password    : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : String? = null,
    @SerializedName("address"      ) var address     : String? = null,
    @SerializedName("image_url"    ) var imageUrl    : String? = null,
    @SerializedName("city"         ) var city        : String? = null,
    @SerializedName("createdAt"    ) var createdAt   : String? = null,
    @SerializedName("updatedAt"    ) var updatedAt   : String? = null

)