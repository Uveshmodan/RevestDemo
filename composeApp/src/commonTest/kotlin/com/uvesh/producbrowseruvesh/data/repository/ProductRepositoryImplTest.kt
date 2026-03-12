package com.uvesh.producbrowseruvesh.data.repository

import com.uvesh.producbrowseruvesh.data.fake.FakeProductApiClient
import com.uvesh.producbrowseruvesh.data.util.fakeProductDto
import com.uvesh.productbrowser.data.repository.ProductRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductRepositoryImplTest {

    private val fakeApi = FakeProductApiClient()
    private val repository = ProductRepositoryImpl(fakeApi)

    @BeforeTest
    fun setup() {
        fakeApi.shouldThrow = false
        fakeApi.errorMessage = "Error"
    }

    // ─── getProducts ───────────────────────────────────────

    @Test
    fun `getProducts returns success with mapped products`() = runTest {
        fakeApi.productsResult = listOf(fakeProductDto())

        val result = repository.getProducts()

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("Fake Product", result.getOrNull()?.first()?.title)
    }

    @Test
    fun `getProducts returns failure when api throws`() = runTest {
        fakeApi.shouldThrow = true
        fakeApi.errorMessage = "Network error"

        val result = repository.getProducts()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    // ─── searchProducts ────────────────────────────────────

    @Test
    fun `searchProducts returns filtered results`() = runTest {
        fakeApi.searchResult = listOf(fakeProductDto(title = "Phone"))

        val result = repository.searchProducts("phone")

        assertTrue(result.isSuccess)
        assertEquals("Phone", result.getOrNull()?.first()?.title)
    }

    @Test
    fun `searchProducts returns failure when api throws`() = runTest {
        fakeApi.shouldThrow = true
        fakeApi.errorMessage = "Search failed"

        val result = repository.searchProducts("phone")

        assertTrue(result.isFailure)
        assertEquals("Search failed", result.exceptionOrNull()?.message)
    }

    // ─── getProductsByCategory ─────────────────────────────

    @Test
    fun `getProductsByCategory returns success`() = runTest {
        fakeApi.categoryResult = listOf(fakeProductDto(category = "electronics"))

        val result = repository.getProductsByCategory("electronics")

        assertTrue(result.isSuccess)
        assertEquals("electronics", result.getOrNull()?.first()?.category)
    }

    @Test
    fun `getProductsByCategory returns failure when api throws`() = runTest {
        fakeApi.shouldThrow = true
        fakeApi.errorMessage = "Category error"

        val result = repository.getProductsByCategory("electronics")

        assertTrue(result.isFailure)
        assertEquals("Category error", result.exceptionOrNull()?.message)
    }

    // ─── getProductDetails ─────────────────────────────────

    @Test
    fun `getProductDetails returns success for valid id`() = runTest {
        fakeApi.productDetailResult = fakeProductDto(id = 1)

        val result = repository.getProductDetails(1L)

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.id)
    }

    @Test
    fun `getProductDetails returns failure for invalid id`() = runTest {
        fakeApi.shouldThrow = true
        fakeApi.errorMessage = "Not found"

        val result = repository.getProductDetails(999L)

        assertTrue(result.isFailure)
        assertEquals("Not found", result.exceptionOrNull()?.message)
    }
}