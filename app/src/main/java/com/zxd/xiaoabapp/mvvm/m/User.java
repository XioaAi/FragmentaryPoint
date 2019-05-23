package com.zxd.xiaoabapp.mvvm.m;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zxd.xiaoabapp.BR;

public class User extends BaseObservable {
    private String userName;

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }
}

