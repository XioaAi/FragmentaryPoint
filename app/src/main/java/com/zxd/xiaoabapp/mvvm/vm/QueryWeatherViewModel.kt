package com.zxd.xiaoabapp.mvvm.vm

import android.util.Log
import android.view.View
import com.google.gson.GsonBuilder
import com.zxd.xiaoabapp.mvvm.m.Weather
import com.zxd.xiaoabapp.mvvm.m.Weatherinfo
import okhttp3.*
import java.io.IOException

class QueryWeatherViewModel{

    var weather = Weatherinfo()
    var gson = GsonBuilder().serializeNulls().create()

    lateinit var call : Call

    fun queryWeather(){
        var okhttp = OkHttpClient()
        var request = Request.Builder().url("http://www.weather.com.cn/data/cityinfo/101210101.html")
                .get().build()
        call = okhttp.newCall(request)
        call.enqueue(object :Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                Log.i("XiaoAi",e?.message)
            }

            override fun onResponse(call: Call?, response: Response?) {
                var dataStr = response?.body()?.string()
                Log.i("XiaoAi",dataStr)
                var weather1 = gson.fromJson<Weather>(dataStr,Weather::class.java)
                //weather.setWeatherinfo(weather1.getWeatherinfo())
                weather.setCity(weather1.getWeatherinfo().getCity())
                weather.setCityid(weather1.getWeatherinfo().getCityid())
                weather.setTemp1(weather1.getWeatherinfo().getTemp1())
                weather.setTemp2(weather1.getWeatherinfo().getTemp2())
                weather.setWeather(weather1.getWeatherinfo().getWeather())
                weather.setPtime(weather1.getWeatherinfo().getPtime())
            }
        })
    }




    fun cancelRequest(){
        call?.cancel()
    }

}