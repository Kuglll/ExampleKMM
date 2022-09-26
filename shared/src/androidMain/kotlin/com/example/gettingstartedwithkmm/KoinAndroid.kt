package com.example.gettingstartedwithkmm

import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module

actual val platformModule = module {
    single<Settings> {
        AndroidSettings(get())
    }
}