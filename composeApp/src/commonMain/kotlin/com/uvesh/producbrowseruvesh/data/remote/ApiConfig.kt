package com.uvesh.productbrowser.data.remote

object ApiConfig {
    const val BASE_URL = "https://dummyjson.com"
    object Endpoints {
        const val PRODUCTS = "/products"
        const val SEARCH = "/products/search"
        fun category(category: String) = "/products/category/$category"
    }
}