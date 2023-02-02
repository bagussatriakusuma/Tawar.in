package com.example.tawarin.Utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {
    const val baseUrl = "https://a19f-158-140-170-51.ap.ngrok.io/api/v1/"
    const val baseUrlImgBB = "https://api.imgbb.com/1/"
    const val ApiKeyImgBB = "a466117e35d1867b443534cc97c097d2"

    const val RetrofitAuth = "RetrofitAuth"
    const val RetrofitProduct = "RetrofitProduct"
    const val RetrofitImgBB = "RetrofitImgBB"

    const val Pref_Name = "User"
    val TOKEN_KEY = stringPreferencesKey("TOKEN")

    const val ProductName = "NAMA"
    const val ImageUrl = "IMAGEURL"
    const val BasePrice = "BASEPRICE"
    const val Description = "DESCRIPTION"
    const val Category = "KATEGORI"
    const val Location  = "LOCATION"
}