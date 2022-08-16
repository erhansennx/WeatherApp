package com.erhansen.weatherapp.service

import com.erhansen.weatherapp.model.WeatherModel
import com.erhansen.weatherapp.utils.ConstantAPI
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(ConstantAPI.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getWeather(): Single<WeatherModel> {
        return api.getWeather()
    }

}