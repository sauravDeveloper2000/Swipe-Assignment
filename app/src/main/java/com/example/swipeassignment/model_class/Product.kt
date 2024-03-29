package com.example.swipeassignment.model_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This represents the type of product which we get from server when we make network request.
 * Like what properties he has. Like in below case it has 5 fields or properties.
 */
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