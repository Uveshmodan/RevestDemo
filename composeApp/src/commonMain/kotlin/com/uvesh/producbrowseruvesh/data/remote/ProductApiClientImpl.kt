package com.uvesh.productbrowser.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

class ApiException(val code: HttpStatusCode?, message: String, cause: Throwable? = null) : Exception(message, cause)

interface ProductApiClient {
    suspend fun getProducts(): List<ProductDto>
    suspend fun searchProducts(query: String): List<ProductDto>
    suspend fun getProductsByCategory(category: String): List<ProductDto>
    suspend fun getProductById(id: Long): ProductDto
}

class ProductApiClientImpl(private val client: HttpClient): ProductApiClient {
    private suspend inline fun <T> safeRequest(block: () -> T): T {
        try {
            return block()
        } catch (e: ClientRequestException) { // 4xx
            throw ApiException(e.response.status, "Client error ${e.response.status}", e) as Throwable
        } catch (e: ServerResponseException) { // 5xx
            throw ApiException(e.response.status, "Server error ${e.response.status}", e)
        } catch (e: ResponseException) {
            // generic response error
            throw ApiException(e.response.status, "HTTP error ${e.response.status}", e)
        } catch (e: Exception) {
            throw ApiException(null, "Network or parse error: ${e.message}", e)
        }
    }

    override suspend fun getProducts(): List<ProductDto> {
        val res: ProductsResponse = client.get(ApiConfig.BASE_URL + ApiConfig.Endpoints.PRODUCTS).body()
        return res.products
    }

    override suspend fun searchProducts(query: String): List<ProductDto> {
        val res: ProductsResponse = client.get(ApiConfig.BASE_URL + ApiConfig.Endpoints.SEARCH) {
            parameter("q", query)
        }.body()
        return res.products
    }

    override suspend fun getProductsByCategory(category: String): List<ProductDto> {
        val res: ProductsResponse = client.get(ApiConfig.BASE_URL + ApiConfig.Endpoints.category(category)).body()
        return res.products
    }

    override suspend fun getProductById(id: Long): ProductDto = safeRequest {
        // endpoint: /products/{id}
        client.get("/products/$id").body()
    }
}