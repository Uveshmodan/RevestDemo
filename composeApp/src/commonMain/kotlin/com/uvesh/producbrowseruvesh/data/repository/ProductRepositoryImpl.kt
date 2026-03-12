package com.uvesh.productbrowser.data.repository

import com.uvesh.productbrowser.data.remote.ProductApiClient
import com.uvesh.productbrowser.data.remote.toDomain
import com.uvesh.productbrowser.domain.model.Product
import com.uvesh.productbrowser.domain.repository.ProductRepository

class ProductRepositoryImpl(private val api: ProductApiClient) : ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> = runCatching {
        api.getProducts().map { it.toDomain() }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>> = runCatching {
        api.searchProducts(query).map { it.toDomain() }
    }

    override suspend fun getProductsByCategory(category: String): Result<List<Product>> = runCatching {
        api.getProductsByCategory(category).map { it.toDomain() }
    }

    override suspend fun getProductDetails(id: Long): Result<Product> = runCatching {
        api.getProductById(id).toDomain()
    }
}