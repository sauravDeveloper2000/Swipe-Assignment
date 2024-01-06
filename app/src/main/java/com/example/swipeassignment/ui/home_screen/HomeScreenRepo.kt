package com.example.swipeassignment.ui.home_screen

import com.example.swipeassignment.api.ProductApiService
import com.example.swipeassignment.model_class.Product
import com.example.swipeassignment.repo_section.Repo

class HomeScreenRepo(
    private val productApiService: ProductApiService
): Repo {
    override suspend fun getListOfProducts(): List<Product> {
        return productApiService.getListOfProducts()
    }
}