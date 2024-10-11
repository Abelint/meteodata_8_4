package com.lekciya.meteodata_8_4

import WeatherService
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var customGraphView: CustomGraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация кастомного графика
        customGraphView = findViewById(R.id.customGraphView)

        // Получение данных о погоде асинхронно
        val weatherService = WeatherService()
        weatherService.fetchWeatherData { times, temperatures ->
            // После получения данных обновляем UI (график)
            runOnUiThread {
                customGraphView.setData(temperatures)
            }
        }
    }
}
