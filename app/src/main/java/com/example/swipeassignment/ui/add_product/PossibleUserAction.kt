package com.example.swipeassignment.ui.add_product

sealed interface PossibleUserAction {
    data class OnProductNameChange(val productName: String): PossibleUserAction
    data class OnProductPriceChange(val productPrice: String): PossibleUserAction
    data class OnProductTaxChange(val taxOnProduct: String): PossibleUserAction
    data class OnProductTypeChange(val productType: String): PossibleUserAction
}