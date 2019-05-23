package com.zxd.xiaoabapp.mvvm.m;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

public class Weather extends BaseObservable {
    /*lateinit var city:String
    lateinit var cityid:String
    lateinit var temp1 :String
    lateinit var temp2:String
    lateinit var weather:String
    lateinit var img1:String
    lateinit var img2:String
    lateinit var ptime:String*/

    public Weatherinfo weatherinfo ;

    @Bindable
    public Weatherinfo getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(Weatherinfo weatherinfo) {
        this.weatherinfo = weatherinfo;
        notifyPropertyChanged(BR.weather);
    }


}


