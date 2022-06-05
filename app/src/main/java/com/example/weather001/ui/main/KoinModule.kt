package com.example.weather001.ui.main

import com.example.weather001.ui.main.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.weather001.ui.main.model.repository.Repository
import com.example.weather001.ui.main.model.repository.RepositoryImpl


val appModule = module {

    single<Repository> { RepositoryImpl() }
    //View models
    viewModel { MainViewModel(get()) }

    viewModel { DetailsViewModel(get()) }
}