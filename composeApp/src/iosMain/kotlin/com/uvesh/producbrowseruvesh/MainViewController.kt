package com.uvesh.producbrowseruvesh

import androidx.compose.ui.window.ComposeUIViewController
import com.uvesh.producbrowseruvesh.di.initKoin
import com.uvesh.productbrowser.presentation.ui.App
import com.uvesh.productbrowser.presentation.viewmodel.ProductListViewModel
import org.koin.mp.KoinPlatform.getKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin() // 👈 starts koin before composable runs
    }
) {
    val viewModel: ProductListViewModel = getKoin().get()
    App(viewModel)
}