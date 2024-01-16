package com.example.swipeassignment.ui.add_product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeassignment.model_class.NewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddProductScreenViewModel @Inject constructor(
    private val addProductRepo: AddProductRepo
) : ViewModel() {

    var _addProductUiState by mutableStateOf<AddProductUiState>(AddProductUiState.Initial)
        private set

    var doProductAddedSuccessfully = MutableStateFlow(false)
        private set
    var productName by mutableStateOf("")
        private set

    var productType by mutableStateOf("")
        private set

    var productPrice by mutableStateOf("")
        private set

    var productTax by mutableStateOf("")
        private set

    var validationSucceeded by mutableStateOf(false)
        private set

    var isProductNameEmpty by mutableStateOf(false)
        private set

    var isProductPriceEmpty by mutableStateOf(false)
        private set

    var isProductTaxEmpty by mutableStateOf(false)
        private set

    var isProductTypeEmpty by mutableStateOf(false)
        private set

    /**
     * OnUserAction():- This sets states i.e those 4 text fields.
     */
    fun onUserAction(userAction: PossibleUserAction) {

        when (userAction) {
            is PossibleUserAction.OnProductNameChange -> {
                productName = userAction.productName
            }

            is PossibleUserAction.OnProductPriceChange -> {
                productPrice = userAction.productPrice
            }

            is PossibleUserAction.OnProductTaxChange -> {
                productTax = userAction.taxOnProduct
            }

            is PossibleUserAction.OnProductTypeChange -> {
                productType = userAction.productType
            }
        }

    }

    fun doResetAllValues() {
        productPrice = ""
        productType = ""
        productTax = ""
        productName = ""
        isProductNameEmpty = false
        isProductTypeEmpty = false
        isProductTaxEmpty = false
        isProductPriceEmpty = false
    }

    /**
     * addProductToServer():- This method adds new product to server.
     *  But before he adds new product to server, 1st he will do validation check whether all fields are filled or not.
     *  Based upon this if or else block got execute.
     *  Also he does 2nd check i.e, isProductNameEmpty. Because if fields are empty or not, based upon this he sets boolean value.
     *  Which in UI sets color scheme of fields. For error red color and for success it revert back error color.
     */
    fun addProductToServer() {
        //Before product is going to add on server do validation.
        if (doValidation()){
            Log.d("Tag1", "Validation Succeeded")
            isProductNameEmpty = productName.isEmpty()
            isProductTypeEmpty = productType.isEmpty()
            isProductPriceEmpty = productPrice.isEmpty()
            isProductTaxEmpty = productTax.isEmpty()
            validationSucceeded = true

            /**
             * After validation we create new product which we want to add to server.
             */
            val product = NewProduct(
                price = productPrice.toDouble().toString(),
                tax = productTax.toDouble().toString(),
                productName = productName,
                productType = productType
            )

            viewModelScope.launch{
                _addProductUiState = AddProductUiState.Loading
                _addProductUiState = try {
                    val response = addProductRepo.addNewProduct(
                        productPrice = product.price,
                        productTax = product.tax,
                        productType = product.productType,
                        productName = productName
                    )
                    Log.d("Tag1", "$response.")
                    AddProductUiState.OnSuccessState(response = response)

                } catch (e: IOException){
                    Log.d("Tag1", "Something Went Wrong Check your connection!")
                    AddProductUiState.OnFailure(response = e.message.toString())
                }
            }

        } else{
            /**
             * If validation failed, then set boolean value to true.
             * Causing error color scheme around text field.
             */
            Log.d("Tag1", "Validation Failed")
            isProductNameEmpty = productName.isEmpty()
            isProductTypeEmpty = productType.isEmpty()
            isProductPriceEmpty = productPrice.isEmpty()
            isProductTaxEmpty = productTax.isEmpty()
            validationSucceeded = false
        }
    }

    /**
     * doValidation:- This method checks if all the fields are filled or not.
     *  If any field is empty, then validation got failed.
     *  But if all fields are filled, then validation got succeeded.
     */
    private fun doValidation(): Boolean {
        return ((productName.isNotEmpty()) && (productType.isNotEmpty()) && (productPrice.isNotEmpty()) && (productTax.isNotEmpty()))
    }
}