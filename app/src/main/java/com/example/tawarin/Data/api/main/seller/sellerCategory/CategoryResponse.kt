package com.example.tawarin.Data.api.main.seller.sellerCategory

import com.google.gson.annotations.SerializedName

data class CategoryResponse (

    @SerializedName("status" ) var status : String,
    @SerializedName("data"   ) var data   : ArrayList<Data> = arrayListOf()

) {
    data class Data (

        @SerializedName("id"        ) var id        : Int,
        @SerializedName("name"      ) var name      : String,
        @SerializedName("createdAt" ) var createdAt : String,
        @SerializedName("updatedAt" ) var updatedAt : String

    )
}
