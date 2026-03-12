package com.uvesh.productbrowser.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvesh.productbrowser.presentation.viewmodel.ProductListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(viewModel: ProductListViewModel) {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "listScreen") {
            composable("listScreen") {
                ProductListScreen(
                    viewModel = viewModel,
                    onClickDetails = {
                        navController.navigate("detailsScreen")
                    }
                )
            }

            composable("detailsScreen") {
                ProductDetailScreen(
                    product = viewModel.selectedProduct.value,
                    onBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}