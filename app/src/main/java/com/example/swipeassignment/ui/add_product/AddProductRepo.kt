package com.example.swipeassignment.ui.add_product

import com.example.swipeassignment.api.ProductApiService
import com.example.swipeassignment.model_class.NewProduct
import com.example.swipeassignment.model_class.Product
import com.example.swipeassignment.model_class.ResponseBodyy
import retrofit2.http.Field
import javax.inject.Inject


class AddProductRepo @Inject constructor(
    private val productApiService: ProductApiService
){
    suspend fun addNewProduct(
        productName: String,
        productType: String,
        productPrice: String,
        productTax: String
    ): ResponseBodyy {
        return productApiService.addNewProduct(
            productName = productName,
            productType = productType,
            productPrice = productPrice,
            productTax = productTax
        )
    }
}