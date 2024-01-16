package com.example.swipeassignment.ui.add_product

import com.example.swipeassignment.model_class.Product
import com.example.swipeassignment.model_class.ResponseBodyy

sealed interface AddProductUiState {
    data object Initial: AddProductUiState
    data object Loading: AddProductUiState
    data class OnSuccessState(val response: ResponseBodyy): AddProductUiState
    data class OnFailure(val response: String): AddProductUiState
}