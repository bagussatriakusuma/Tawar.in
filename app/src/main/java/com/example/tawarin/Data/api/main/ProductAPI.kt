package com.example.tawarin.Data.api.main

import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderRequest
import com.example.tawarin.Data.api.main.buyer.buyerOrder.BuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerOrder.GetBuyerOrderResponse
import com.example.tawarin.Data.api.main.buyer.buyerProduct.ProductByIdResponse
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.tawarin.Data.api.main.buyer.buyerProduct.ProductResponse
import com.example.tawarin.Data.api.main.notification.NotificationResponse
import com.example.tawarin.Data.api.main.seller.sellerApproveOrder.ApproveOrderRequest
import com.example.tawarin.Data.api.main.seller.sellerApproveOrder.ApproveOrderResponse
import com.example.tawarin.Data.api.main.seller.sellerCategory.CategoryByIdResponse
import com.example.tawarin.Data.api.main.seller.sellerOrder.SellerOrderResponse
import com.example.tawarin.Data.api.main.seller.sellerProduct.DeleteSellerProductResponse
import com.example.tawarin.Data.api.main.seller.sellerProduct.SellerProductResponse
import com.example.tawarin.Data.api.main.seller.sellerProduct.UpdateProductResponse
import com.example.tawarin.Data.api.main.seller.sellerUploadProduct.UploadProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ProductAPI {
    @GET("seller/category")
    suspend fun getCategory()
    : Response<CategoryResponse>

    @GET("seller/category/{id}")
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): Response<CategoryByIdResponse>

    @GET("buyer/product")
    suspend fun getProductBuyer(
        @Query("status") status: String,
        @Query("category_id") categoryId: String,
        @Query("search") search: String
    ): Response<ProductResponse>

    @GET("buyer/product/{id}")
    suspend fun getProductBuyerById(
        @Path("id") id: Int
    ): Response<ProductByIdResponse>

    @GET("buyer/order")
    suspend fun getBuyerOrder(
        @Header("Authorization") token: String
    ): Response<List<GetBuyerOrderResponse>>

    @POST("buyer/order")
    suspend fun postBuyerOrder(
        @Header("Authorization") token: String,
        @Body requestBuyerOrder: BuyerOrderRequest
    ): Response<BuyerOrderResponse>

    @Multipart
    @POST("seller/product")
    suspend fun uploadProduct(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_id") categoryId: List<Int>,
//        @Part("location") location: RequestBody?,
    ): Response<UploadProductResponse>

    @GET("seller/product/all")
    suspend fun getSellerProduct(
        @Header("Authorization") token: String
    ): Response<SellerProductResponse>

    @GET("seller/order")
    suspend fun getSellerOrder(
        @Query("status") status: String,
        @Header("Authorization") token: String
    ): Response<List<SellerOrderResponse>>

    @Multipart
    @PUT("seller/product/{id}")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part file: MultipartBody.Part?,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_id") categoryId: List<Int>,
//        @Part("location") location: RequestBody?,
    ): Response<UpdateProductResponse>

    @DELETE("seller/product/{id}")
    suspend fun deleteSellerProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteSellerProductResponse>

    @PATCH("seller/order/{id}")
    suspend fun approveOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body approveOrderRequest: ApproveOrderRequest
    ): Response<ApproveOrderResponse>

    @GET("notification")
    suspend fun getNotification(
        @Header("Authorization") token: String,
    ): Response<List<NotificationResponse>>
}