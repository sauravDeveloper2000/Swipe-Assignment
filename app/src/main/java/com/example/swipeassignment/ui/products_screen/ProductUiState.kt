package com.example.swipeassignment.ui.products_screen

import com.example.swipeassignment.model_class.Product

/**
 * This sealed interface is used to handle recent web request response.
 *  Like at first it take time, so we show Loading state.
 *  After Loading, if everything goes write, then it enters  Success state.
 *  Otherwise it enters into Error state.
 */
sealed interface ProductUiState {
    data object Loading: ProductUiState
    data class OnSuccess(val products: List<Product>): ProductUiState
    data class OnError(val errorMessage: String?): ProductUiState
}