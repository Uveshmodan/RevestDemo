package com.uvesh.producbrowseruvesh.data.util

import com.uvesh.productbrowser.data.remote.ProductDto

fun fakeProductDto(
    id: Int = 1,
    title: String = "Fake Product",
    description: String = "Desc",
    price: Double = 9.99,
    rating: Double = 4.5,
    brand: String = "Brand",
    thumbnail: String = "url",
    category: String = "general"
) = ProductDto(
    id = id,
    title = title,
    description = description,
    price = price,
    rating = rating,
    brand = brand,
    thumbnail = thumbnail,
    category = category
)