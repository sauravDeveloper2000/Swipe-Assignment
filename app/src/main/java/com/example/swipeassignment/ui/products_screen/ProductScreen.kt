package com.example.swipeassignment.ui.products_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swipeassignment.R
import com.example.swipeassignment.components.InErrorState
import com.example.swipeassignment.components.InLoadingState
import com.example.swipeassignment.components.VerticalSpacer
import com.example.swipeassignment.model_class.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productUiState: ProductUiState,
    addProduct: () -> Unit,
    productsScreenViewModel: ProductsScreenViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.first_screen_top_app_bar),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addProduct()
                }
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.add_new_product),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->

        when (productUiState) {
            ProductUiState.Loading -> {
                InLoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is ProductUiState.OnError -> {
                InErrorState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is ProductUiState.OnSuccess -> {
                Products(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    products = productUiState.products,
                    productsScreenViewModel = productsScreenViewModel
                )
            }
        }
    }
}

@Composable
fun Products(
    modifier: Modifier,
    products: List<Product>,
    productsScreenViewModel: ProductsScreenViewModel
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 10.dp)
    ) {
        item {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(),
                productsScreenViewModel = productsScreenViewModel
            )
            VerticalSpacer(space = 10)
        }

        if (products.isEmpty()) {
            item {
                Text(text = stringResource(id = R.string.if_list_is_empty))
            }
        } else {
            items(products) { product ->
                SingleProductItem(product = product)
                VerticalSpacer(space = 10)
            }
        }
    }

}

@Composable
fun SingleProductItem(
    product: Product
) {
    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .clip(shape = MaterialTheme.shapes.small),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.default_image),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Name:- ${product.productName}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.W600,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "Type:- ${product.productType}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.W600,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Column {
                    Text(
                        text = "Tax:- ${product.tax}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.W600,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "Price:- ${product.price}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.W600,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    productsScreenViewModel: ProductsScreenViewModel
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productsScreenViewModel.value,
            onValueChange = { inputString ->
                productsScreenViewModel.initializeSearchField(inputString)
            },
            label = {
                Text(text = stringResource(id = R.string.search))
            },
            trailingIcon = {
                Image(
                    modifier = Modifier
                        .clickable {
                            productsScreenViewModel.doSearch()
                        },
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(25.dp),
            keyboardActions = KeyboardActions.Default
        )
    }
}
