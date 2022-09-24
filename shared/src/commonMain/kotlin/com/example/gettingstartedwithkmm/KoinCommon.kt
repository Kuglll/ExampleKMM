package com.example.gettingstartedwithkmm

import com.example.gettingstartedwithkmm.domain.reminders.RemindersRepository
import com.example.gettingstartedwithkmm.domain.reminders.RemindersViewModel
import com.example.gettingstartedwithkmm.ui.shared.base.MainViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

object Modules {
    val repositories = module {
        factory { RemindersRepository() }
    }
    val viewModels = module {
        factory { RemindersViewModel(get()) }
        factory { MainViewModel(get()) }
    }
    val platforms = module {
        factory { Platform() }
    }
}

fun initKoin(
    appModule: Module = module { },
    repositoriesModule: Module = Modules.repositories,
    viewModelsModule: Module = Modules.viewModels,
    platformsModule: Module = Modules.platforms
): KoinApplication = startKoin {
    modules(
        appModule,
        platformsModule,
        repositoriesModule,
        viewModelsModule
    )
}