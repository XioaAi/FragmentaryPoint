package com.zxd.xiaoabapp.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.animation.AnimationSet;
import android.widget.Toast;

import com.zxd.xiaoabapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationSetActivity extends AppCompatActivity {

    /*float dowmX = -1;
    float downY = -1;

    float upX = -1;
    float upY = -1;
    @BindView(R.id.cardview1)
    CardView cardview1;
    @BindView(R.id.cardview2)
    CardView cardview2;

    AnimatorSet animationSet ;

    ObjectAnimator anim1;
    ObjectAnimator anim5;
    ObjectAnimator anim6;
    ObjectAnimator anim2;
    ObjectAnimator anim3;
    ObjectAnimator anim4;
    ObjectAnimator anim7;
    ObjectAnimator anim8;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set);
        ButterKnife.bind(this);

        /*ObjectAnimator anim44 = ObjectAnimator.ofFloat(cardview2, "scaleY", 1f, 1.2f);
        ObjectAnimator anim11 = ObjectAnimator.ofFloat(cardview2, "translationX", 0,40);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim11).with(anim44);
        animatorSet.setDuration(4);
        animatorSet.start();

        anim1 = ObjectAnimator.ofFloat(cardview2, "translationX", 40, 800);
        anim1.setDuration(3000);
        anim5 = ObjectAnimator.ofFloat(cardview1, "scaleY", 1f, 1.2f);
        anim5.setDuration(2000);
        anim6 = ObjectAnimator.ofFloat(cardview1, "translationX", 840, 40);
        anim6.setDuration(3000);
        anim2 = ObjectAnimator.ofFloat(cardview2, "translationX", 800, -40);
        anim2.setDuration(3000);
        anim3 = ObjectAnimator.ofFloat(cardview1, "scaleY", 1f, 1.2f);
        anim3.setDuration(2000);
        anim4 = ObjectAnimator.ofFloat(cardview2, "scaleY", 1.2f, 1f);
        anim4.setDuration(2000);
        anim7 = ObjectAnimator.ofFloat(cardview1, "translationX", 0, 840);
        anim7.setDuration(3000);
        anim8 = ObjectAnimator.ofFloat(cardview2, "translationX", 40, 0);
        anim8.setDuration(3000);*/
    }


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dowmX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                if (upX - dowmX > 100) {
                    animationSet = new AnimatorSet();
                    animationSet.play(anim3).with(anim1);
                    animationSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationSet = new AnimatorSet();
                            cardview1.bringToFront();
                            animationSet.play(anim2).with(anim4);
                            animationSet.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animationSet.start();

                    Toast.makeText(AnimationSetActivity.this, "左滑", Toast.LENGTH_LONG).show();
                } else if (upX - dowmX < -100) {
                    animationSet = new AnimatorSet();
                    animationSet.play(anim4).with(anim7).with(anim8);
                    animationSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationSet = new AnimatorSet();
                            cardview1.bringToFront();
                            animationSet.play(anim5).with(anim6);
                            animationSet.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animationSet.start();
                    Toast.makeText(AnimationSetActivity.this, "右滑", Toast.LENGTH_LONG).show();
                }

                break;
        }
        return super.onTouchEvent(event);
    }*/
}
