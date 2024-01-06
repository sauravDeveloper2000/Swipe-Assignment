package com.example.swipeassignment.repo_section

import com.example.swipeassignment.model_class.Product

interface Repo {
    suspend fun getListOfProducts(): List<Product>
}