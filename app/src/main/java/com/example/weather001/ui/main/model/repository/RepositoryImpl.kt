package com.example.weather001.ui.main.model.repository

import com.example.weather001.ui.main.model.repository.entities.Weather
import com.example.weather001.ui.main.model.repository.entities.getRussianCities
import com.example.weather001.ui.main.model.repository.entities.getWorldCities
import com.example.weather001.ui.main.model.repository.WeatherLoader
import com.example.weather001.ui.main.model.repository.rest.WeatherRepo

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(lat: Double, lon: Double): Weather {
        val dto = WeatherRepo.api.getWeather(lat, lon).execute().body()
        return Weather(
            temperature = dto?.fact?.temp ?: 0,
            feelsLike = dto?.fact?.feelsLike ?: 0,
            condition = dto?.fact?.condition


        )
    }

    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}