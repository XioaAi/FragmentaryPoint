package com.zxd.xiaoabapp.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.utils.DensityUtils;
import com.zxd.xiaoabapp.view.ScrollView;

public class DrawScrollActivity extends AppCompatActivity {

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_scroll);

        scrollView = findViewById(R.id.reelView);

        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        ObjectAnimator roation = ObjectAnimator.ofFloat(scrollView,"rotation",0,360*3);
        roation.setDuration(1000);
        ObjectAnimator translation = ObjectAnimator.ofFloat(scrollView,"translationY",dm.heightPixels-160,0);
        translation.setDuration(1000);
        //translation.setInterpolator(new BounceInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(roation).with(translation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup.LayoutParams layoutParams = scrollView.getLayoutParams();
                layoutParams.height = DensityUtils.dip2px(DrawScrollActivity.this,600);
                scrollView.setLayoutParams(layoutParams);

                scrollView.startExplain();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();

    }
}
