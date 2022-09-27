package com.example.gettingstartedwithkmm

import com.example.gettingstartedwithkmm.db.DatabaseHelper
import com.example.gettingstartedwithkmm.domain.reminders.RemindersRepository
import com.example.gettingstartedwithkmm.domain.reminders.RemindersViewModel
import com.example.gettingstartedwithkmm.ui.shared.base.MainViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

object Modules {
    val repositories = module {
        factory { RemindersRepository(get()) }
    }
    val viewModels = module {
        factory { RemindersViewModel(get()) }
        factory { MainViewModel(get(), get()) }
    }
    val core = module {
        factory { Platform() }
        factory { DatabaseHelper(get()) }
    }
}

fun initKoin(
    appModule: Module = module { },
    repositoriesModule: Module = Modules.repositories,
    viewModelsModule: Module = Modules.viewModels,
    platformsModule: Module = Modules.core
): KoinApplication = startKoin {
    modules(
        appModule,
        platformsModule,
        repositoriesModule,
        viewModelsModule,
        platformModule
    )
}