package com.example.myapplication.network

import com.airbnb.mvrx.sample.models.JokesResponse
import com.example.myapplication.WEATHER_API_KEY
import com.example.myapplication.models.CityWeatherResponse
import com.example.myapplication.models.Joke
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {
    @Headers("Accept: application/json")
    @GET("search")
    suspend fun search(
        @Query("query") query: String? = null,
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 20
    ): JokesResponse

    @Headers("Accept: application/json")
    @GET("j/{id}")
    suspend fun fetch(@Path("id") id: String): Joke

    @Headers("Accept: application/json")
    @GET("/")
    suspend fun random(): Joke

    // 获取天气数据
    @Headers("Accept: application/json")
    @GET("weatherInfo")
    suspend fun getWeatherByCode(@Query("key") key: String = WEATHER_API_KEY,@Query("extensions")extensions:String="all", @Query("city") cityCode: Int): CityWeatherResponse
}
