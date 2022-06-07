package com.example.weather001.ui.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather001.ui.main.model.repository.AppState
import com.example.weather001.ui.main.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val weatherLiveData: LiveData<AppState> get() = localLiveData

    fun loadData(lat: Double, lon: Double) {
        localLiveData.value = AppState.Loading
        val job = viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getWeatherFromServer(lat, lon)
            localLiveData.postValue(AppState.Success(listOf(data)))
        }

    }
}