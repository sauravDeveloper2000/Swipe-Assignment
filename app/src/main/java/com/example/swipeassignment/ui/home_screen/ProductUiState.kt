package com.example.swipeassignment.ui.home_screen

import com.example.swipeassignment.model_class.Product

sealed interface ProductUiState {
    data object Loading: ProductUiState
    data class onSuccess(val products: List<Product>): ProductUiState
    data class onError(val errorMessage: String?): ProductUiState
}