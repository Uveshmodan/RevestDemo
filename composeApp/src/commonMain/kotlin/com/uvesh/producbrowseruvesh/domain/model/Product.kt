package com.uvesh.productbrowser.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val rating: Double? = null,
    val brand: String? = null,
    val thumbnail: String? = null,
    val category: String? = null,
)