package com.example.swipeassignment.model_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val image: String,
    val price: Double,
    @SerialName("product_name")
    val productName: String,
    @SerialName("product_type")
    val productType: String,
    val tax: Double
)