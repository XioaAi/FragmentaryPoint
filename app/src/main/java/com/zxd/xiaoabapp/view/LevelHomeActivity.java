/*
package com.zxd.xiaoabapp.view;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xes.chinese.banxue.R;
import com.xes.chinese.banxue.base.Chsbx_BaseActivity;
import com.xes.chinese.banxue.bean.LevelHomeStatusBean;
import com.xes.chinese.banxue.view.weight.LevelHomeLastLevelView;
import com.xes.chinese.banxue.view.weight.LevelHomeView;
import com.xes.chinese.banxue.view.weight.animation.CustomRotateAnim;
import com.xes.cloudlearning.bcmpt.route.RouteManager;

import static com.xes.chinese.chsbcmpt.router.ChsRoutePathContant.chs_bx.ACTIVITY_BX_ATLAS_SHOW_HOME;
import static com.xes.chinese.chsbcmpt.router.ChsRoutePathContant.chs_bx.ACTIVITY_BX_LEVELE_HOME;
import static com.xes.chinese.chsbcmpt.router.ChsRoutePathContant.chs_bx.ACTIVITY_BX_LEVELE_SECONDE_HOME;

*/
/**
 * @Description:
 * @ProjectName: CloudLearning_Chinese_Android
 * @Package: com.xes.chinese.banxue.view.activity LevelHomeActivity
 * @CreateDate: 2019/4/11 11:11
 * @Version: 1.0
 * @Author: wangziheng
 *//*

@Route(path = ACTIVITY_BX_LEVELE_HOME)
public class LevelHomeActivity extends Chsbx_BaseActivity {

    private int mCurrentLevelIndex;
    private LevelHomeView mLevelHomeView;

    private Button mGoAtlasBtn;

    @Override
    protected void bindData() {
        mLevelHomeView = (LevelHomeView) findViewById(R.id.chsbx_activity_levele_home_view);
        mGoAtlasBtn = (Button) findViewById(R.id.chsbx_activity_levele_home_go_atlas_btn);
        LevelHomeStatusBean levelStatusBean;
        for (int i = 0; i < 10; i++) {
            levelStatusBean = new LevelHomeStatusBean();
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200, 200);
            imageView.setLayoutParams(layoutParams);

            levelStatusBean.setCurrentLevelIndex(i);
            imageView.setTag(levelStatusBean);
            if (i == 5) {
                // 设置当前关卡
                mCurrentLevelIndex = i;
                levelStatusBean.setCurrentLevel(true);
                levelStatusBean.setVIPLevel(false);
                imageView.setImageResource(R.mipmap.viewtools_ic_launcher);
                mLevelHomeView.addView(imageView);
            } else if (i == 9) {
                // 设置最终关卡
                LevelHomeLastLevelView levelHomeLastLevelView = new LevelHomeLastLevelView(this);
                levelHomeLastLevelView.setAlLevelNum(9);
                levelHomeLastLevelView.setCurrentLeve(mCurrentLevelIndex);
                for(int l = 0 ; l <= 9 ; l ++){
                    ImageView starImg = new ImageView(this);
                    ViewGroup.LayoutParams starLp = new ViewGroup.LayoutParams(40, 40);
                    starImg.setLayoutParams(starLp);
                    starImg.setImageDrawable(getDrawable(R.mipmap.viewtools_ic_launcher));
                    if(l < 6){
                        starImg.setImageTintList(ColorStateList.valueOf(getColor(R.color.bcm_color_ar7_black)));
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
                ii.setImageDrawable(getDrawable(R.mipmap.viewtools_ic_launcher));
                levelHomeLastLevelView.addView(ii);
                mLevelHomeView.addView(levelHomeLastLevelView);
                CustomRotateAnim rotateAnim = CustomRotateAnim.getCustomRotateAnim();
                rotateAnim.setDuration(500);
                rotateAnim.setRepeatCount(-1);
                rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                ii.startAnimation(rotateAnim);
            } else {
                mLevelHomeView.addView(imageView);
                levelStatusBean.setCurrentLevel(false);
                levelStatusBean.setVIPLevel(false);
                imageView.setImageResource(R.mipmap.viewtools_ic_launcher);
            }
            imageView.setOnClickListener(v ->
                    {
                        LevelHomeStatusBean levelHomeStatusBean = (LevelHomeStatusBean) imageView.getTag();
                        if (levelHomeStatusBean.getCurrentLevelIndex() > mCurrentLevelIndex) {
                            Toast.makeText(LevelHomeActivity.this, "请先通关前面关卡", Toast.LENGTH_SHORT).show();
                        } else {
                            RouteManager.build(ACTIVITY_BX_LEVELE_SECONDE_HOME).navigation(LevelHomeActivity.this);
                        }
                    }
            );
        }
        mGoAtlasBtn.setOnClickListener(v -> RouteManager.build(ACTIVITY_BX_ATLAS_SHOW_HOME).navigation(LevelHomeActivity.this));
    }

    @Override
    public int getLayout() {
        return R.layout.chsbx_activity_level_home_layout;
    }
}
*/
