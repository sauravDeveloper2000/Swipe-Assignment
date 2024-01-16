package com.example.swipeassignment.model_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The response we get from server, when we add new product into server.
 */
@Serializable
data class ResponseBodyy(
    val message: String,
    @SerialName(value = "product_details")
    val productDetails: Product,
    @SerialName(value = "product_id")
    val productId: Int,
    val success: Boolean
)