package com.erhansen.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(

    @SerializedName("base")
    val base: String, //-
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int, //-
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int,//-
    @SerializedName("id")
    val id: Int,//-
    val main: Main,
    @SerializedName("name")
    val name: String, //-
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int, //-
    @SerializedName("visibility")
    val visibility: Int,//-
    val weather: List<Weather>,
    val wind: Wind
)