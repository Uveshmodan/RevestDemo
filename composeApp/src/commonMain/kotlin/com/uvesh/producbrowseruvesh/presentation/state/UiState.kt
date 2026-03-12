package com.uvesh.producbrowseruvesh.presentation.state

import com.uvesh.productbrowser.domain.model.Product

data class UiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)