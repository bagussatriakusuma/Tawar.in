package com.example.tawarin.Repository

import com.example.tawarin.Data.api.main.ProductAPI
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
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductAPI) {
    suspend fun getCategory(): Response<CategoryResponse> {
        return api.getCategory()
    }

    suspend fun getCategoryById(id: Int): Response<CategoryByIdResponse> {
        return api.getCategoryById(id = id)
    }

    suspend fun getProductBuyer(status: String, categoryId: String, search: String): Response<ProductResponse> {
        return api.getProductBuyer(status = status, categoryId = categoryId, search = search)
    }

    suspend fun getProductBuyerById(id: Int): Response<ProductByIdResponse> {
        return api.getProductBuyerById(id = id)
    }

    suspend fun getBuyerOrder(token: String): Response<List<GetBuyerOrderResponse>> {
        return api.getBuyerOrder(token = "Bearer $token")
    }

    suspend fun postBuyerOrder(token: String, requestBuyerOrder: BuyerOrderRequest): Response<BuyerOrderResponse> {
        return api.postBuyerOrder(token = "Bearer $token", requestBuyerOrder = requestBuyerOrder)
    }

    suspend fun uploadProductSeller(
        token: String,
        file : MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        categoryIds: List<Int>,
//        location: RequestBody,
    ): Response<UploadProductResponse> {
        return api.uploadProduct("Bearer $token" , file, name, description, base_price, categoryIds)
    }

    suspend fun getSellerProduct(token: String): Response<SellerProductResponse> {
        return api.getSellerProduct(token = "Bearer $token")
    }

    suspend fun getSellerOrder(token: String, status: String): Response<List<SellerOrderResponse>> {
        return api.getSellerOrder(token = "Bearer $token", status = status)
    }

    suspend fun updateProduct(
        token: String,
        id: Int,
        file : MultipartBody.Part?,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        categoryId: List<Int>,
//        location: RequestBody,
    ): Response<UpdateProductResponse> {
        return api.updateProduct("Bearer $token", id, file, name, description, base_price, categoryId)
    }

    suspend fun deleteSellerProduct(token: String, id: Int): Response<DeleteSellerProductResponse> {
        return api.deleteSellerProduct(token = "Bearer $token", id = id)
    }

    suspend fun getNotification(token: String): Response<List<NotificationResponse>> {
        return api.getNotification(token = "Bearer $token")
    }

    suspend fun approveOrder(token: String, id: Int, approveOrderRequest: ApproveOrderRequest)
            : Response<ApproveOrderResponse> {
        return api.approveOrder(token = "Bearer $token", id = id, approveOrderRequest = approveOrderRequest)
    }
}