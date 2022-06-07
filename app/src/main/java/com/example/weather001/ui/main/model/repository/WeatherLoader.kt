package com.example.weather001.ui.main.model.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather001.ui.main.model.repository.entities.rest_entities.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


object WeatherLoader {
    fun loadWeather(lat: Double, lon: Double): WeatherDTO? {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
        lateinit var urlConnection: HttpsURLConnection

        return try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.addRequestProperty(
                "X-Yandex-API-Key", "3589d9e5-bb67-4397-8574-d8e99cc2fac3"
            )
            urlConnection.readTimeout = 10000
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                getLinesForOld(bufferedReader)
            } else {
                getLines(bufferedReader)
            }

            Gson().fromJson(lines, WeatherDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            urlConnection.disconnect()
        }
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}