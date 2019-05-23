package com.zxd.xiaoabapp.ui

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zxd.xiaoabapp.R
import com.zxd.xiaoabapp.databinding.ActivityMvvmdemoBinding
import com.zxd.xiaoabapp.mvvm.m.User
import com.zxd.xiaoabapp.mvvm.m.Weatherinfo
import com.zxd.xiaoabapp.mvvm.vm.QueryWeatherViewModel

class MVVMDemoActivity : AppCompatActivity() {

    lateinit var viewModel: QueryWeatherViewModel
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bind = DataBindingUtil.setContentView<ActivityMvvmdemoBinding>(this, R.layout.activity_mvvmdemo)
        viewModel = QueryWeatherViewModel()
        user = User()
        bind.click = viewModel
        bind.weatherinfo = viewModel.weather
        bind.user = user

        test()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelRequest()
    }

    fun test(){

        object: Thread(){
            override fun run() {
                super.run()
                var i = 0
                do {
                    Thread.sleep(2000)
                    user.setUserName("user " + i++)
                }while (true)
            }
        }.start()

    }
}
