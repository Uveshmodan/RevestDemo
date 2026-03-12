package com.uvesh.producbrowseruvesh.data

import android.app.Application
import com.uvesh.producbrowseruvesh.di.sharedModule
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(sharedModule)
        }
    }
}