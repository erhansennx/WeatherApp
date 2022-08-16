package com.erhansen.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erhansen.weatherapp.R
import com.erhansen.weatherapp.databinding.ActivityMainBinding
import com.erhansen.weatherapp.model.WeatherModel
import com.erhansen.weatherapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        with(activityMainBinding) {
            mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
            mainViewModel.refreshData()
        }

        observeLiveData()
        mainViewModel.refreshData()

    }


    private fun observeLiveData() {

        with(activityMainBinding) {
            //DATALARI GÖZLEMLİYORUZ.
            mainViewModel.weatherLiveData.observe(this@MainActivity, Observer { data ->
                //main view modelin içerisindeki weather live data boş değilse bu satıra girecek.u
                data?.let { data ->

                    //Glide
                    cityCodeTextView.text = data.sys.country
                    cityNameTextView.text = data.name
                    degreeTextView.text = data.main.temp.toString() + " C"
                    windSpeedTextView.text = data.wind.speed.toString()
                    latitudeTextView.text = data.coord.lat.toString()
                    longitudeTextView.text = data.coord.lon.toString()

                }
            })

            mainViewModel.weatherError.observe(this@MainActivity, Observer { error ->
                error?.let {
                    if (it) {
                        println("Error!")
                        progressBar.visibility = View.GONE
                    }
                }
            })

            mainViewModel.weatherLoading.observe(this@MainActivity, Observer { loading ->
                loading?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
            })

        }

    }




}