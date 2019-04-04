package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.widget.QMUIItemViewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ViewPagerTabLinearlayout extends LinearLayout {

    private int mDefaultNormalColor = R.color.black;
    private int mDefaultSelectedColor = R.color.colorPrimary;

    public ViewPagerTabLinearlayout(Context context) {
        super(context);
    }

    public ViewPagerTabLinearlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerTabLinearlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewPagerTabLinearlayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置 Tab 正常状态下的颜色
     */
    public void setDefaultNormalColor(@ColorInt int defaultNormalColor) {
        mDefaultNormalColor = defaultNormalColor;
    }

    /**
     * 设置 Tab 选中状态下的颜色
     */
    public void setDefaultSelectedColor(@ColorInt int defaultSelectedColor) {
        mDefaultSelectedColor = defaultSelectedColor;
    }

    private int selectPos = 0;
    private ViewPager mViewPager;

    public void init(Context context,ViewPager viewPager,List<String> tabs){
        this.mViewPager = viewPager;
        for(int a = 0;a<tabs.size();a++){
            final int b = a;
            TextView tab = (TextView) LayoutInflater.from(context).inflate(R.layout.viewpager_tab_item,null);
            tab.setText(tabs.get(a));
            tab.setTag(a);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(b);
                    selectPos = b;
                    changeColor();
                }
            });
            addView(tab, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPos = position;
                changeColor();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void changeColor(){
        for(int a = 0;a<getChildCount();a++){
            if(a == selectPos){
                ((TextView)getChildAt(a)).setTextColor(getResources().getColor(mDefaultSelectedColor));
            }else{
                ((TextView)getChildAt(a)).setTextColor(getResources().getColor(mDefaultNormalColor));
            }
        }
    }


}
