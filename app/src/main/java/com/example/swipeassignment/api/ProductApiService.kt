package com.example.swipeassignment.api

import com.example.swipeassignment.model_class.Product
import retrofit2.http.GET

interface ProductApiService {
    @GET("api/public/get")
    suspend fun getListOfProducts(): List<Product>
}