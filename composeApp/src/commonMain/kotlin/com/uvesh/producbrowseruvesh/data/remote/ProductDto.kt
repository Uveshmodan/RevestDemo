package com.uvesh.productbrowser.data.remote

import com.uvesh.productbrowser.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int?,
    val title: String?,
    val description: String?,
    val price: Double?,
    val discountPercentage: Double? = null,
    val rating: Double?,
    val brand: String? = null,
    val thumbnail: String?,
    val images: List<String>? = emptyList(),
    val category: String?
)

@Serializable
data class ProductsResponse(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

fun ProductDto.toDomain() = Product(
    id = id,
    title = title,
    description = description,
    price = price,
    rating = rating,
    brand = brand,
    thumbnail = thumbnail,
    category = category
)