package com.example.swipeassignment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.swipeassignment.R

/**
 * This component shows when we do network request and that takes some time, so we show this
 * loading component.
 */
@Composable
fun InLoadingState(
    modifier: Modifier
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

/**
 * This component shows when theirs some internet issue
 * like no internet from user side or maybe from server side like 505(internal server error)
 */
@Composable
fun InErrorState(
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = R.drawable.no_internet_connection),
                contentDescription = null
            )
            Text(
                text = "No Internet Connection!"
            )
        }
    }
}

/**
 * Vertical Spacer used to create space between 2 components in vertical manner.
 */
@Composable
fun VerticalSpacer(
    space: Int
) {
    Spacer(modifier = Modifier.height(space.dp))
}