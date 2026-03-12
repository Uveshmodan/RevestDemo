package com.uvesh.productbrowser.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.uvesh.producbrowseruvesh.presentation.state.UiState
import com.uvesh.productbrowser.domain.model.Product
import com.uvesh.productbrowser.presentation.viewmodel.ProductListViewModel

@Composable
fun ProductListScreen(viewModel: ProductListViewModel, onClickDetails: () -> Unit) {

    val state by viewModel.productList.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        topBar = {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    viewModel.searchQuery(it)
                },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
        ) {
            when {
                state.isLoading -> LoadingState()
                state.error != null -> ErrorState(state)
                else -> {
                    SuccessState(state, viewModel, onClickDetails)
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) { CircularProgressIndicator() }
}

@Composable
private fun ErrorState(state: UiState) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) { Text("${state.error}") }
}

@Composable
private fun SuccessState(
    state: UiState,
    viewModel: ProductListViewModel,
    onClickDetails: () -> Unit
) {
    if (state.products.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.products.size) { product ->
                ProductGridItem(product = state.products[product]) {
                    viewModel.selectedProduct.value = state.products[product]
                    onClickDetails()
                }
            }
        }
    } else {
        ErrorState(UiState(error = "No products found!"))
    }
}

@Composable
private fun ProductGridItem(product: Product, content: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(15.dp),
            )
            .border(width = 1.dp, color = DividerDefaults.color, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                content()
            }
    ) {
        AsyncImage(
            model = product.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
        )

        HorizontalDivider()

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 2.dp)
                .height(34.dp)
        ) {
            Text(
                text = product.title?:"",
                fontSize = 12.sp,
                maxLines = 2,
                lineHeight = 16.sp,
            )
        }

        Text(
            text = "$${product.price}",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
        )
    }
}
