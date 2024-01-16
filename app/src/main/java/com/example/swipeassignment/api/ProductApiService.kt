package com.example.swipeassignment.api

import com.example.swipeassignment.model_class.NewProduct
import com.example.swipeassignment.model_class.Product
import com.example.swipeassignment.model_class.ResponseBodyy
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.io.File

interface ProductApiService {

    /**
     * Gives list of products from server.
     */
    @GET("api/public/get")
    suspend fun getListOfProducts(): List<Product>

    /**
     * Adds new product into server.
     */
    @FormUrlEncoded
    @POST("api/public/add")
    suspend fun addNewProduct(
        @Field("product_name") productName: String,
        @Field("product_type") productType: String,
        @Field("price") productPrice: String,
        @Field("tax") productTax: String
    ): ResponseBodyy

}