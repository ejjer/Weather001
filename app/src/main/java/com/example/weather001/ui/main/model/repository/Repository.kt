package com.example.weather001.ui.main.model.repository

import com.example.weather001.ui.main.model.repository.entities.Weather


interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}