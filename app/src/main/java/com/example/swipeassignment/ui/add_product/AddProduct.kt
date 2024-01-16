package com.example.swipeassignment.ui.add_product

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.swipeassignment.R
import com.example.swipeassignment.components.InLoadingState
import com.example.swipeassignment.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct(
    navigateBack: () -> Unit,
    addProductScreenViewModel: AddProductScreenViewModel
) {
    val context = LocalContext.current
    val openDialog = remember {
        mutableStateOf(false)
    }
    var addProductUiState = addProductScreenViewModel._addProductUiState

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Image(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.second_screen_top_app_bar)
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /**
             * Consists 4 outlined text fields, to take user info about the new product.
             */
            InsertProductInfo(
                addProductScreenViewModel = addProductScreenViewModel
            )
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = MaterialTheme.shapes.small,
                onClick = {

                    addProductScreenViewModel.addProductToServer()
                    if (addProductScreenViewModel.validationSucceeded) {
                        Toast.makeText(context, "Validation Succeeded", Toast.LENGTH_SHORT).show()
                        openDialog.value = true
                    } else {
                        Toast.makeText(
                            context,
                            "Validation Failed, Check all fields.",
                            Toast.LENGTH_LONG
                        ).show()
                        openDialog.value = false
                    }

                }
            ) {
                Text(text = stringResource(id = R.string.add_product))
            }
            /**
             * Dialog Box Logic Which happens on button click. And checks if validation succeeded or not.
             * If succeeded, then dialog box appears.
             * If not, then dailog box wont appear.
             */
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onDismissRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.product_addition_status),
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    text = {
                        when (addProductUiState) {
                            AddProductUiState.Initial -> {
                                Text(text = stringResource(id = R.string.initial))
                            }

                            AddProductUiState.Loading -> {
                                InLoadingState(modifier = Modifier.fillMaxWidth())
                            }

                            is AddProductUiState.OnFailure -> {
                                Text(
                                    text = stringResource(id = R.string.on_fail),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            is AddProductUiState.OnSuccessState -> {
                                Column {
                                    Text(
                                        text = "${addProductUiState.response.message}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Text(
                                        text = "Product Details which you have inserted.",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "1. Product Name - ${addProductUiState.response.productDetails.productName}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "2. Product Type - ${addProductUiState.response.productDetails.productType}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "3. Product Price - ${addProductUiState.response.productDetails.price}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "4. Tax on Product - ${addProductUiState.response.productDetails.tax}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        when (addProductUiState) {
                            AddProductUiState.Initial -> {

                            }
                            AddProductUiState.Loading -> {

                            }
                            is AddProductUiState.OnFailure -> {
                                TextButton(
                                    onClick = {
                                        openDialog.value = false
                                    }
                                ) {
                                    Text(text = stringResource(id = R.string.on_validation_failed))
                                }
                            }
                            is AddProductUiState.OnSuccessState -> {
                                TextButton(
                                    onClick = {
                                        navigateBack()
                                    }
                                ) {
                                    Text(text = stringResource(id = R.string.on_validation_succeed))
                                }
                            }
                        }
                    },
                    dismissButton = null
                )
            }
        }
    }
}

@Composable
fun InsertProductInfo(
    addProductScreenViewModel: AddProductScreenViewModel
) {

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addProductScreenViewModel.productName,
            onValueChange = {
                addProductScreenViewModel.onUserAction(
                    PossibleUserAction.OnProductNameChange(it)
                )
            },
            isError = addProductScreenViewModel.isProductNameEmpty,
            label = {
                Text(text = "Enter Product Name*")
            }
        )

        VerticalSpacer(space = 10)

        ProductType(
            addProductScreenViewModel = addProductScreenViewModel
        )

        VerticalSpacer(space = 10)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addProductScreenViewModel.productPrice,
            onValueChange = {
                addProductScreenViewModel.onUserAction(
                    PossibleUserAction.OnProductPriceChange(it)
                )
            },
            label = {
                Text(text = "Enter Product Price🫰 in 0.0 format*")
            },
            isError = addProductScreenViewModel.isProductPriceEmpty,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        VerticalSpacer(space = 10)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = addProductScreenViewModel.productTax,
            onValueChange = {
                addProductScreenViewModel.onUserAction(
                    PossibleUserAction.OnProductTaxChange(
                        it
                    )
                )
            },
            isError = addProductScreenViewModel.isProductTaxEmpty,
            label = {
                Text(text = "Enter Tax value in 0.0 format*")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

    }
}

/**
 * Composable for Product Type. Used Exposed Drop Down composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductType(
    addProductScreenViewModel: AddProductScreenViewModel
) {

    val productType =
        listOf("Other", "Food", "Vehicle", "Electronics", "Furniture", "Kitchen Utensils", "Clothes", "Toys", "Jewelry")
    var expanded by remember { mutableStateOf(false) }
    var selectedProductType by remember { mutableStateOf(productType[0]) }
    val context = LocalContext.current

    // menu box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // textfield
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .menuAnchor(), // menuAnchor modifier must be passed to the text field for correctness.
            readOnly = true,
            value = addProductScreenViewModel.productType,
            label = {
                Text(text = "Select Product Type*")
            },
            onValueChange = {},
            isError = addProductScreenViewModel.isProductTypeEmpty,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            // menu items
            productType.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedProductType = selectionOption
                        addProductScreenViewModel.onUserAction(
                            PossibleUserAction.OnProductTypeChange(selectedProductType)
                        )
                        Toast.makeText(
                            context,
                            "You have selected $selectionOption type",
                            Toast.LENGTH_SHORT
                        ).show()
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

