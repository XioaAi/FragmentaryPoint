package com.zxd.xiaoabapp.ui;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zxd.xiaoabapp.LevelHomeStatusBean;
import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.view.LevelHomeLastLevelView;
import com.zxd.xiaoabapp.view.LevelHomeView;
import com.zxd.xiaoabapp.view.LevelStartView;

public class CustomViewBitmapActivity extends AppCompatActivity {

    private LevelHomeView mLevelHomeView;
    private int mCurrentLevelIndex;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_bitmap);

        mLevelHomeView = findViewById(R.id.levelHomeView);
        LevelHomeStatusBean levelStatusBean;
        for (int i = 0; i < 10; i++) {
            levelStatusBean = new LevelHomeStatusBean();
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(150, 150);
            imageView.setLayoutParams(layoutParams);

            levelStatusBean.setCurrentLevelIndex(i);
            imageView.setTag(levelStatusBean);
            if (i == 5) {
                // 设置当前关卡
                mCurrentLevelIndex = i;
                levelStatusBean.setCurrentLevel(true);
                levelStatusBean.setVIPLevel(false);
                imageView.setImageResource(R.mipmap.ic_launcher_round);
                mLevelHomeView.addView(imageView);
            } else if (i == 9) {
                // 设置最终关卡
                LevelHomeLastLevelView levelHomeLastLevelView = new LevelHomeLastLevelView(this);
                //levelHomeLastLevelView.setAlLevelNum(9);
                //levelHomeLastLevelView.setCurrentLeve(mCurrentLevelIndex);
                for(int l = 0 ; l <= 9 ; l ++){
                    ImageView starImg = new ImageView(this);
                    ViewGroup.LayoutParams starLp = new ViewGroup.LayoutParams(40, 40);
                    starImg.setLayoutParams(starLp);
                    starImg.setImageDrawable(getDrawable(R.mipmap.ic_launcher_round));
                    if(l < 6){
                        starImg.setImageTintList(ColorStateList.valueOf(getColor(R.color.blue_btn_bg_color)));
                    }
                    levelHomeLastLevelView.addView(starImg);
                }
                levelStatusBean.setCurrentLevel(false);
                levelStatusBean.setVIPLevel(true);
                levelHomeLastLevelView.setTag(levelStatusBean);
                ImageView ii = new ImageView(this);
                ViewGroup.LayoutParams centerLp = new ViewGroup.LayoutParams(300, 150);
                ii.setScaleType(ImageView.ScaleType.FIT_XY);
                ii.setLayoutParams(centerLp);
                ii.setTag("true");
                ii.setImageDrawable(getDrawable(R.mipmap.ic_launcher_round));
                levelHomeLastLevelView.addView(ii);
                mLevelHomeView.addView(levelHomeLastLevelView);
                /*CustomRotateAnim rotateAnim = CustomRotateAnim.getCustomRotateAnim();
                rotateAnim.setDuration(500);
                rotateAnim.setRepeatCount(-1);
                rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                ii.startAnimation(rotateAnim);*/
            } else {
                mLevelHomeView.addView(imageView);
                levelStatusBean.setCurrentLevel(false);
                levelStatusBean.setVIPLevel(false);
                imageView.setImageResource(R.mipmap.ic_launcher_round);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CustomViewBitmapActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
