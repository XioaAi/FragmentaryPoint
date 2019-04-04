package com.zxd.xiaoabapp.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxd.xiaoabapp.R;

/**
 * Created by Administrator on 2018/3/21.
 */
public class TwoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Tag","TwoFragment_onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Tag","TwoFragment_onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Tag","TwoFragment_onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Tag","TwoFragment_onPause");
    }


}
