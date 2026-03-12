package com.uvesh.producbrowseruvesh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.uvesh.productbrowser.presentation.ui.App
import com.uvesh.productbrowser.presentation.viewmodel.ProductListViewModel
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ProductListViewModel = GlobalContext.get().get()

            App(viewModel)
        }
    }
}