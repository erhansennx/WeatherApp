package com.erhansen.weatherapp.service

import com.erhansen.weatherapp.model.WeatherModel
import com.erhansen.weatherapp.utils.ConstantAPI
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherAPI {

    //GET,POST
    @GET(ConstantAPI.EXT)
    fun getWeather(): Single<WeatherModel>


}