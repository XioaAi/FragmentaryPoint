package com.zxd.xiaoabapp.ui;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.view.CustomHorizationProgress;
import com.zxd.xiaoabapp.view.CustomProgress;
import com.zxd.xiaoabapp.view.HorizontalProgressbarWithProgress;

public class CustomProgressViewActivity extends AppCompatActivity {

    CustomProgress customProgress;

    CustomHorizationProgress customHorizationProgress;
    //HorizontalProgressbarWithProgress customHorizationProgress;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = customHorizationProgress.getProgress();
            customHorizationProgress.setProgress(++progress);
            if(progress>=100){
                handler.removeMessages(0);
            }
            handler.sendEmptyMessageDelayed(0,100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_view);

        customProgress = findViewById(R.id.custom_progress);
        customHorizationProgress = findViewById(R.id.custom_horization_progress);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(customProgress,"progress",0,65);
        objectAnimator.setDuration(1000);
        objectAnimator.start();


        customHorizationProgress.setMax(100);
        customHorizationProgress.setProgress(0);

        //从0更新到100
        if (customHorizationProgress.getProgress() == 100) {
            customHorizationProgress.setProgress(0);
        }
        handler.sendEmptyMessage(0);

    }
}
