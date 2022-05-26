package com.example.weather001.ui.main.model.repository

import com.example.weather001.ui.main.model.repository.entities.Weather

class RepositoryImpl: Repository {
    override fun getWeatherFromServer() = Weather()
    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}