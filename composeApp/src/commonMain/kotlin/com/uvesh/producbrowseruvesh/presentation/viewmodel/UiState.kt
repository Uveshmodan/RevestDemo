package com.uvesh.productbrowser.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvesh.producbrowseruvesh.presentation.state.UiState
import com.uvesh.productbrowser.domain.model.Product
import com.uvesh.productbrowser.domain.usecase.GetProductsUseCase
import com.uvesh.productbrowser.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val selectedProduct = mutableStateOf<Product?>(null)

    private val _productList = MutableStateFlow(UiState(isLoading = true))
    val productList = searchQuery
        .debounce(500L)
        .flatMapLatest { search(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState(isLoading = true)
        )

    fun searchQuery(query: String) {
        _searchQuery.value = query
    }

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _productList.value = _productList.value.copy(isLoading = true, error = null)
            val res = getProductsUseCase()
            _productList.value = if (res.isSuccess) UiState(isLoading = false, products = res.getOrNull() ?: emptyList())
            else UiState(isLoading = false, error = res.exceptionOrNull()?.message ?: "")
        }
    }

    private fun search(query: String): Flow<UiState> = flow {
        emit(UiState(isLoading = true))
        val res = searchProductsUseCase(query)
        if (res.isSuccess) {
            emit(UiState(
                isLoading = false,
                products = res.getOrNull() ?: emptyList()
            ))
        } else {
            emit(UiState(
                isLoading = false,
                error = res.exceptionOrNull()?.message ?: ""
            ))
        }
    }
}