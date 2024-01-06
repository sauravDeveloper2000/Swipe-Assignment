package com.example.swipeassignment.ui.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeassignment.repo_section.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repo: Repo
): ViewModel() {

    var _responseState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
        private set

    init {
        getProducts()
    }
    private fun getProducts(){
        viewModelScope.launch {
            _responseState.value = ProductUiState.Loading
            _responseState.value = try {
                val response = repo.getListOfProducts()
                ProductUiState.onSuccess(products = response)
            } catch (e: IOException){
                ProductUiState.onError(errorMessage = e.message.toString())
            }
        }
    }
}