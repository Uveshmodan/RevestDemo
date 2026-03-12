package com.uvesh.productbrowser.domain.usecase

import com.uvesh.productbrowser.domain.repository.ProductRepository

class GetProductsUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke() = repo.getProducts()
}

class SearchProductsUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke(query: String) = repo.searchProducts(query)
}