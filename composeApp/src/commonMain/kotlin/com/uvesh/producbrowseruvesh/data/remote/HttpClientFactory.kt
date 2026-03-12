package com.uvesh.productbrowser.data.remote

import io.ktor.client.HttpClient

expect fun createHttpClient(baseUrl: String = ApiConfig.BASE_URL): HttpClient