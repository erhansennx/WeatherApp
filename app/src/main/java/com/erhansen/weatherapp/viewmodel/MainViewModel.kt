package com.erhansen.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erhansen.weatherapp.model.WeatherModel
import com.erhansen.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val weatherAPIService = WeatherAPIService()
    private val compositeDisposable = CompositeDisposable() //biriken call'lar temizlenir. kullan at objesidir.

    val weatherLiveData = MutableLiveData<WeatherModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoading = MutableLiveData<Boolean>()

    //Weather Refresh
    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        weatherLoading.value = true
        compositeDisposable.add(
            weatherAPIService.getWeather()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                     weatherLiveData.value = t
                     weatherError.value = false
                     weatherLoading.value = false
                    }
                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoading.value = false
                        println("Error : ${e.message}")
                    }
                })
        )
    }


}