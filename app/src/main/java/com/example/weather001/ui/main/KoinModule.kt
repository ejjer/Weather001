package com.example.weather001.ui.main

import androidx.room.Room
import com.example.weather001.ui.main.details.DetailsViewModel
import com.example.weather001.ui.main.history.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.weather001.ui.main.model.repository.repository.Repository
import com.example.weather001.ui.main.model.repository.repository.RepositoryImpl
import org.koin.android.ext.koin.androidContext
import com.example.weather001.ui.main.model.repository.database.Database


val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "app_database.db"
        ).build()
    }
    single<Repository> { RepositoryImpl(get()) }
    //View models
    viewModel { MainViewModel(get()) }

    viewModel { DetailsViewModel(get()) }
    viewModel { HistoryViewModel(get ())}

}