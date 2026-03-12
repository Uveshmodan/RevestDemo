package com.uvesh.producbrowseruvesh.data.fake

import com.uvesh.producbrowseruvesh.data.util.fakeProductDto
import com.uvesh.productbrowser.data.remote.ProductApiClient
import com.uvesh.productbrowser.data.remote.ProductDto

class FakeProductApiClient : ProductApiClient {

    var productsResult: List<ProductDto> = emptyList()
    var searchResult: List<ProductDto> = emptyList()
    var categoryResult: List<ProductDto> = emptyList()
    var productDetailResult: ProductDto = fakeProductDto()

    var shouldThrow: Boolean = false
    var errorMessage: String = "Error"

    override suspend fun getProducts(): List<ProductDto> {
        if (shouldThrow) throw Exception(errorMessage)
        return productsResult
    }

    override suspend fun searchProducts(query: String): List<ProductDto> {
        if (shouldThrow) throw Exception(errorMessage)
        return searchResult
    }

    override suspend fun getProductsByCategory(category: String): List<ProductDto> {
        if (shouldThrow) throw Exception(errorMessage)
        return categoryResult
    }

    override suspend fun getProductById(id: Long): ProductDto {
        if (shouldThrow) throw Exception(errorMessage)
        return productDetailResult
    }
}