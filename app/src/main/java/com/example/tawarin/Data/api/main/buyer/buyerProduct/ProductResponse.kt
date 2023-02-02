package com.example.tawarin.Data.api.main.buyer.buyerProduct

import com.google.gson.annotations.SerializedName

data class ProductResponse (

    @SerializedName("status"     ) var status     : String?           = null,
    @SerializedName("produk"     ) var produk     : ArrayList<Produk> = arrayListOf(),
    @SerializedName("page"       ) var page       : Int?              = null,
    @SerializedName("per_page"   ) var perPage    : Int?              = null,
    @SerializedName("totalRows"  ) var totalRows  : Int?              = null,
    @SerializedName("totalPages" ) var totalPages : Int?              = null

) {
    data class Produk (

        @SerializedName("id"          ) var id          : Int?              = null,
        @SerializedName("name"        ) var name        : String?           = null,
        @SerializedName("description" ) var description : String?           = null,
        @SerializedName("base_price"  ) var basePrice   : Int?              = null,
        @SerializedName("image_url"   ) var imageUrl    : ArrayList<String> = arrayListOf(),
        @SerializedName("location"    ) var location    : String?           = null,
        @SerializedName("user_id"     ) var userId      : Int?              = null,
        @SerializedName("createdAt"   ) var createdAt   : String?           = null,
        @SerializedName("updatedAt"   ) var updatedAt   : String?           = null,
        @SerializedName("category"    ) var category    : Category?         = Category()

    ) {
        data class Category (

            @SerializedName("id"   ) var id   : Int?    = null,
            @SerializedName("name" ) var name : String? = null

        )
    }
}