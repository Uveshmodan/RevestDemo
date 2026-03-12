package com.uvesh.productbrowser.domain.repository

import com.uvesh.productbrowser.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun searchProducts(query: String): Result<List<Product>>
    suspend fun getProductsByCategory(category: String): Result<List<Product>>
    suspend fun getProductDetails(id: Long): Result<Product>
}