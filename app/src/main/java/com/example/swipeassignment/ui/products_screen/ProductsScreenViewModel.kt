package com.example.swipeassignment.ui.products_screen

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
class ProductsScreenViewModel @Inject constructor(
    private val repo: Repo
): ViewModel() {

    var _responseState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
        private set

    var value by mutableStateOf("")
        private set

    fun initializeSearchField(
        value: String
    ){
        this.value = value
    }

    init {
        getProducts()
    }

    /**
     * getProducts():- Below function brings data into app. And the data is list of products.
     */
    private fun getProducts(){
        viewModelScope.launch {
            _responseState.value = ProductUiState.Loading
            _responseState.value = try {
                val response = repo.getListOfProducts()
                ProductUiState.OnSuccess(products = response)
            } catch (e: IOException){
                ProductUiState.OnError(errorMessage = e.message.toString())
            }
        }
    }

    /**
     * doSearch():- Search for product being entered by user.
     *  And if that product exists, then it is being show to user.
     *  But if not, then getProducts() got execute which shows all product to user.
     */
    fun doSearch(){
        if (value.isNotEmpty()){
            viewModelScope.launch {
                _responseState.value = ProductUiState.Loading
                _responseState.value =try {
                    val response = repo.getListOfProducts()
                    val filterList = response.filter { (it.productName == value) || (it.productType == value) }
                    ProductUiState.OnSuccess(products = filterList)
                } catch (e: Exception) {
                    ProductUiState.OnError(errorMessage = e.message)
                }
            }
        } else{
            getProducts()
        }
    }

}