package com.example.weather001.ui.main.model.repository.repository

import com.example.weather001.ui.main.model.repository.entities.Weather
import com.example.weather001.ui.main.model.repository.entities.getRussianCities
import com.example.weather001.ui.main.model.repository.entities.getWorldCities
import com.example.weather001.ui.main.model.repository.database.Database
import com.example.weather001.ui.main.model.repository.database.HistoryEntity
import com.example.weather001.ui.main.model.repository.entities.City
import com.example.weather001.ui.main.model.repository.rest.WeatherRepo

class RepositoryImpl(private val db:Database) : Repository {
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
    override fun saveEntity(weather: Weather) {
        db.historyDao().insert(convertWeatherToEntity(weather))
    }

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(db.historyDao().all())
    }

    private fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
        return entityList.map {
            Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
        }
    }


    private fun convertWeatherToEntity(weather: Weather): HistoryEntity {
        return HistoryEntity(
            0, weather.city.city,
            weather.temperature,
            weather.condition ?: ""
        )
    }
}