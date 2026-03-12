package com.uvesh.producbrowseruvesh.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(sharedModule) // your shared koin module
    }
}