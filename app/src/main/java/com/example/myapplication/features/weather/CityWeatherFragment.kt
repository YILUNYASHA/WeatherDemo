package com.example.myapplication.features.weather

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.*
import com.example.myapplication.R
import com.example.myapplication.core.BaseFragment
import com.example.myapplication.databinding.FragmentTestBinding
import com.example.myapplication.viewbinding.viewBinding
import com.example.myapplication.views.basicRow
import com.example.myapplication.views.marquee


class CityWeatherFragment : BaseFragment(R.layout.fragment_test) {
    private val binding: FragmentTestBinding by viewBinding()
    private val viewModel: CityWeatherViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })
    }

    override fun invalidate() {
        binding.recycler.requestModelBuild()
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        marquee {
            id("marquee")
            title("天气预报")
            subtitle("明日天气预报(点击可重新获取天气数据)")
            clickListener { v-> viewModel.initWeather() }
        }

        state.cities.forEach { city ->
            if (city.casts.isNotEmpty() && city.casts.size > 1) {
                val cast = city.casts[1]
                basicRow {
                    id(city.adcode)
                    title("${city.city}  ${cast.date}")
                    subtitle("日间天气：${cast.dayweather}\n晚间天气：${cast.nightweather}\n温度区间：${cast.nighttemp_float} - ${cast.daytemp_float}度 ")
                }
            }else{
                basicRow {
                    id(city.adcode)
                    title("${city.city}")
                    subtitle("获取天气预报失败:(")
                }
            }
        }
    }

}