package com.example.weather001.ui.main.model.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val city: String,
    val temperature: Int,
    val condition: String
)