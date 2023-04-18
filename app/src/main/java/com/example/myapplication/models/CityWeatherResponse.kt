package com.example.myapplication.models

data class CityWeatherResponse(
    val count: String,
    val info: String,
    val infocode: String,
    val forecasts: List<CityWeather>,
    val status: String
)