package com.example.tawarin.Data.api.main.seller.sellerCategory

import com.google.gson.annotations.SerializedName

data class CategoryByIdResponse (

    @SerializedName("status" ) var status : String? = null,
    @SerializedName("data"   ) var data   : Data?   = Data()

) {
    data class Data (

        @SerializedName("id"        ) var id        : Int?    = null,
        @SerializedName("name"      ) var name      : String? = null,
        @SerializedName("createdAt" ) var createdAt : String? = null,
        @SerializedName("updatedAt" ) var updatedAt : String? = null

    )
}