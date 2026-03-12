package com.uvesh.producbrowseruvesh.di

import com.uvesh.productbrowser.data.remote.ApiConfig
import com.uvesh.productbrowser.data.remote.ProductApiClient
import com.uvesh.productbrowser.data.remote.ProductApiClientImpl
import com.uvesh.productbrowser.data.remote.createHttpClient
import com.uvesh.productbrowser.data.repository.ProductRepositoryImpl
import com.uvesh.productbrowser.domain.repository.ProductRepository
import com.uvesh.productbrowser.domain.usecase.GetProductsUseCase
import com.uvesh.productbrowser.domain.usecase.SearchProductsUseCase
import com.uvesh.productbrowser.presentation.viewmodel.ProductListViewModel
import org.koin.dsl.module

val sharedModule = module {
    single { createHttpClient(ApiConfig.BASE_URL) }        // HttpClient
    single<ProductApiClient> { ProductApiClientImpl(get()) }

    single<ProductRepository> { ProductRepositoryImpl(get()) }
    // register ViewModels / usecases as needed

    // Register the use-case (factory is usually preferred for stateless use-cases)
    factory { GetProductsUseCase(get()) }
    factory { SearchProductsUseCase(get()) }

    // Example ViewModel registration if you use them in shared code
    factory { ProductListViewModel(get(), get()) }

}