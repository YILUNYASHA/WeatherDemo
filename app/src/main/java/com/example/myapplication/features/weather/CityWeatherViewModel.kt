package com.example.myapplication.features.weather

import com.airbnb.mvrx.*
import com.example.myapplication.*
import com.example.myapplication.core.MvRxViewModel
import com.example.myapplication.models.CityWeather
import com.example.myapplication.models.CityWeatherResponse
import com.example.myapplication.network.RemoteService
import org.koin.android.ext.android.inject


data class CityWeatherState(
    /** We use this request to store the list of all jokes. */
    val cities: List<CityWeather> = mutableListOf(),
    /** We use this Async to keep track of the state of the current network request. */
    val request: Async<CityWeatherResponse> = Uninitialized,
    val isShowRows: Boolean = true
) : MavericksState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class CityWeatherViewModel(
    initialState: CityWeatherState,
    private val remoteService: RemoteService
) : MvRxViewModel<CityWeatherState>(initialState) {

    init {
        initWeather()
    }


    fun initWeather() = withState { state ->
        setState {
            copy(cities = emptyList())
        }
        getWeather(City_BeiJing)
        getWeather(City_ShangHai)
        getWeather(City_GuangZhou)
        getWeather(City_ShenZhen)
        getWeather(City_ShenYang)
        getWeather(City_SuZhou)
    }

    // 获取天气
    fun getWeather(city: Int) = withState { state ->
//        if (state.request is Loading) return@withState

        suspend {
            remoteService.getWeatherByCode(cityCode = city)
        }.execute { copy(request = it, cities = cities + (it()?.forecasts ?: emptyList())) }
    }


    /**
     * If you implement MavericksViewModelFactory in your companion object, MvRx will use that to create
     * your ViewModel. You can use this to achieve constructor dependency injection with Mavericks.
     *
     * @see MavericksViewModelFactory
     */
    companion object : MavericksViewModelFactory<CityWeatherViewModel, CityWeatherState> {

        override fun create(viewModelContext: ViewModelContext, state: CityWeatherState): CityWeatherViewModel {
            val service: RemoteService by viewModelContext.activity.inject()
            return CityWeatherViewModel(state, service)
        }
    }
}
