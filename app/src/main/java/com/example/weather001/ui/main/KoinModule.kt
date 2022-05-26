package com.example.weather001.ui.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.weather001.ui.main.model.repository.Repository
import com.example.weather001.ui.main.model.repository.RepositoryImpl
import com.example.weather001.ui.main.MainViewModel

val appModule = module {

    single<Repository>{RepositoryImpl()}
    //View models
    viewModel { MainViewModel(get()) }
}