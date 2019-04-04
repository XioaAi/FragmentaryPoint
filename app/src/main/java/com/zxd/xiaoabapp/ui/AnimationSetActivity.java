package com.zxd.xiaoabapp.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.zxd.xiaoabapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationSetActivity extends AppCompatActivity {

    float dowmX = -1;
    float downY = -1;

    float upX = -1;
    float upY = -1;
    @BindView(R.id.cardview1)
    CardView cardview1;
    @BindView(R.id.cardview2)
    CardView cardview2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set);
        ButterKnife.bind(this);

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(cardview2, "scaleY", 1f, 1.2f);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(cardview2, "translationX", 0,40);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim1).with(anim4);
        animatorSet.setDuration(4);
        animatorSet.start();
    }


    @Override
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
                    ObjectAnimator anim1 = ObjectAnimator.ofFloat(cardview2, "translationX", 40, 800);
                    anim1.setDuration(3000);
                    ObjectAnimator anim5 = ObjectAnimator.ofFloat(cardview2, "translationZ", 0, 20);
                    anim1.setDuration(3000);
                    ObjectAnimator anim6 = ObjectAnimator.ofFloat(cardview2, "translationZ", 0, -20);
                    anim1.setDuration(3000);
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(cardview2, "translationX", 800, -40);
                    anim2.setDuration(3000);
                    ObjectAnimator anim3 = ObjectAnimator.ofFloat(cardview1, "scaleY", 1f, 1.2f);
                    anim3.setDuration(4000);
                    ObjectAnimator anim4 = ObjectAnimator.ofFloat(cardview2, "scaleY", 1.2f, 1f);
                    anim4.setDuration(3000);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(anim1).with(anim3).with(anim4).with(anim5).with(anim6).before(anim2);
                    //animatorSet.play(anim1).before(anim4);
                    //cardview1.bringToFront();
                    //animatorSet.play(anim1).before(anim2);

                    animatorSet.start();

                    Toast.makeText(AnimationSetActivity.this, "左滑", Toast.LENGTH_LONG).show();
                } else if (upX - dowmX < -100) {
                    Toast.makeText(AnimationSetActivity.this, "右滑", Toast.LENGTH_LONG).show();
                } else {

                }

                break;
        }
        return super.onTouchEvent(event);
    }
}
