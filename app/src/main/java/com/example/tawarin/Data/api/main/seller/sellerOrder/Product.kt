package com.example.tawarin.Data.api.main.seller.sellerOrder

import com.google.gson.annotations.SerializedName

data class Product (

    @SerializedName("id"          ) var id          : Int?              = null,
    @SerializedName("name"        ) var name        : String?           = null,
    @SerializedName("description" ) var description : String?           = null,
    @SerializedName("base_price"  ) var basePrice   : Int?              = null,
    @SerializedName("image_url"   ) var imageUrl    : ArrayList<String> = arrayListOf(),
    @SerializedName("location"    ) var location    : String?           = null,
    @SerializedName("user_id"     ) var userId      : Int?              = null,
    @SerializedName("category_id" ) var categoryId  : Int?              = null,
    @SerializedName("status"      ) var status      : String?           = null,
    @SerializedName("createdAt"   ) var createdAt   : String?           = null,
    @SerializedName("updatedAt"   ) var updatedAt   : String?           = null,
    @SerializedName("User"        ) var User        : User?             = User()

)