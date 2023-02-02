package com.example.tawarin.Data.api.main.buyer.buyerProduct

import com.google.gson.annotations.SerializedName

data class ProductByIdResponse (

    @SerializedName("status"   ) var status : String? = null,
    @SerializedName("produk"   ) var produk : Produk? = Produk(),
//    @SerializedName("category" ) var category : Category? = Category(),
//    @SerializedName("user"     ) var user : User? = User()

) {

    data class Produk (

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
        @SerializedName("Category"    ) var Category    : Category?         = Category(),
        @SerializedName("User"        ) var User        : User?             = User()

    )

    data class Category (

        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("name" ) var name : String? = null

    )

    data class User (

        @SerializedName("id"           ) var id          : Int?    = null,
        @SerializedName("full_name"    ) var fullName    : String? = null,
        @SerializedName("email"        ) var email       : String? = null,
        @SerializedName("city"         ) var city        : String? = null,
        @SerializedName("address"      ) var address     : String? = null,
        @SerializedName("phone_number" ) var phoneNumber : String? = null,
        @SerializedName("foto"         ) var foto        : String? = null

    )
}