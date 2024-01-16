package com.example.swipeassignment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swipeassignment.ui.add_product.AddProduct
import com.example.swipeassignment.ui.add_product.AddProductScreenViewModel
import com.example.swipeassignment.ui.products_screen.ProductScreen
import com.example.swipeassignment.ui.products_screen.ProductsScreenViewModel

/**
 * Routes:- Route is basically an string id to each destination.
 * Destination:- Destination is each screen. Like this app consists 2 screens.
 *               So this consists 2 destinations.
 */
enum class Routes{
    ProductsScreen,
    AddProductScreen
}

/**
 * Home screen:- This composable consists of navigation logic. Means navHost, navHostController like that.
 */
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    productsScreenViewModel: ProductsScreenViewModel,
    addProductScreenViewModel: AddProductScreenViewModel
) {

    val productUiState by productsScreenViewModel._responseState.collectAsState()

    NavHost(navController = navController, startDestination = Routes.ProductsScreen.name){
        composable(route = Routes.ProductsScreen.name){
            ProductScreen(
                productUiState = productUiState,
                addProduct = {
                    addProductScreenViewModel.doResetAllValues()
                    navController.navigate(route = Routes.AddProductScreen.name)
                },
                productsScreenViewModel = productsScreenViewModel
            )
        }

        composable(route = Routes.AddProductScreen.name){
            AddProduct(
                navigateBack = {
                    navController.popBackStack()
                },
                addProductScreenViewModel = addProductScreenViewModel
            )
        }
    }
}