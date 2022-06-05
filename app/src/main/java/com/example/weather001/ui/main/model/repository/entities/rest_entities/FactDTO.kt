package com.example.weather001.ui.main.model.repository.entities.rest_entities

import com.google.gson.annotations.SerializedName

data class FactDTO(
    val temp: Int?,
    @SerializedName("feels_like") val feelsLike: Int?,
    val condition: String?
)
