package com.example.swipeassignment.model_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class "NewProduct" is for when we add new product into server.
 * like what properties he holds.
 */
@Serializable
data class NewProduct(
    val price: String,
    @SerialName("product_name")
    val productName: String,
    @SerialName("product_type")
    val productType: String,
    val tax: String
)
