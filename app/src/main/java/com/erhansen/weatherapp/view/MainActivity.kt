package com.erhansen.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.erhansen.weatherapp.R
import com.erhansen.weatherapp.databinding.ActivityMainBinding
import com.erhansen.weatherapp.utils.imageViewProgressBar
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

            swipeRefreshLayout.setOnRefreshListener {
                mainViewModel.refreshData()
                swipeRefreshLayout.isRefreshing = false
            }

        }
        observeLiveData()
    }


    private fun observeLiveData() {

        with(activityMainBinding) {
            //DATALARI GÖZLEMLİYORUZ.
            mainViewModel.weatherLiveData.observe(this@MainActivity, Observer { data ->
                //main view modelin içerisindeki weather live data boş değilse bu satıra girecek.u
                data?.let { data ->

                    val requestOptions = RequestOptions()
                        .placeholder(imageViewProgressBar(this@MainActivity))
                        .error(R.mipmap.ic_launcher_round)
                    //Glide
                    Glide.with(this@MainActivity)
                        .setDefaultRequestOptions(requestOptions)
                        .load("https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")
                        .into(weatherImageView)
                    println("Image : https://openweathermap.org/img/wn/${data.weather[0].icon}.png")

                    cityNameTextView.text = data.name + ", ${data.sys.country}"
                    degreeTextView.text = "Degree \n " +data.main.temp.toInt()/10 + " °C"
                    windSpeedTextView.text = "Wind Speed \n" +data.wind.speed.toString()
                    humidityTextView.text = "Humidity \n" +data.main.humidity.toString()
                    latitudeTextView.text = "Latitude " +data.coord.lat.toString()
                    longitudeTextView.text = "Longitude " +data.coord.lon.toString()

                }
            })

            mainViewModel.weatherError.observe(this@MainActivity, Observer { error ->
                error?.let {
                    if (it) {
                        Toast.makeText(this@MainActivity,"Data download failed!",Toast.LENGTH_LONG).show()
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