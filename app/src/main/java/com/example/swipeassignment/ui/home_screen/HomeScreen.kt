package com.example.swipeassignment.ui.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swipeassignment.R
import com.example.swipeassignment.components.Loading
import com.example.swipeassignment.components.OnError
import com.example.swipeassignment.model_class.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    responseState: ProductUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        }
    ) {innerPadding ->
        when (responseState) {
            ProductUiState.Loading -> {
                Loading(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding))
            }

            is ProductUiState.onError -> {
                OnError(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding))
            }

            is ProductUiState.onSuccess -> {
                ProductListScreen(
                    products = responseState.products,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun ProductListScreen(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            SingleProductItem(product = product)
        }
    }
}

@Composable
fun SingleProductItem(
    product: Product
) {
    Card {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.loading),
                error = painterResource(id = R.drawable.no_internet_connection)
            )
            Text(text = "${product.productType}")
            Text(text = "${product.productType}")
            Text(text = "${product.price}")
            Text(text = "${product.tax}")
        }
    }
}