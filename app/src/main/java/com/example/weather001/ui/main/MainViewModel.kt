package com.example.weather001.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather001.ui.main.model.repository.Repository
import com.example.weather001.ui.main.model.repository.AppState

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val localLiveData = MutableLiveData<AppState>()
    val liveData: LiveData<AppState> get() = localLiveData

    fun getWeather () = getDataFromLocalSource()

    private fun getDataFromLocalSource(){
        localLiveData.value= AppState.Loading
        Thread{
            Thread.sleep(1000)
            localLiveData.postValue(AppState.Success(repository.getWeatherFromServer()))
        }.start()
    }
}