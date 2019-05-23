package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: CloudLearning_Chinese_Android
 * @Package: com.xes.chinese.banxue.view.weightLevelHomeLastLevelView
 * @Description:
 * @Author: wangziheng
 * @CreateDate: 2019/4/30 11:11 AM
 * @Version: 1.0
 */
public class LevelHomeLastLevelView extends ViewGroup {
    /**
     * 屏幕宽高
     */
    private int mScreenWidth, mScreenHeight;
    /**
     * 总关卡数
     */
    private int mAllLevelNum = 9;
    /**
     * 当前关卡
     */
    private int mCurrentLevel = 4;
    private int mRadius;

    private Bitmap mBitmap;
    public LevelHomeLastLevelView(Context context) {
        this(context, null);
    }

    public LevelHomeLastLevelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelHomeLastLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            display.getRealSize(outPoint);
        } else {
            display.getSize(outPoint);
        }
        mScreenHeight = outPoint.y;
        mScreenWidth = outPoint.x;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int resultWidht, resultHeight;
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidht = widthSize;
        } else {
            resultWidht = mScreenWidth - getPaddingStart() - getPaddingEnd();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = heightSize;
        } else {
            resultHeight = mScreenHeight / 3;
        }
        setMeasuredDimension(View.resolveSize(resultWidht, widthMeasureSpec), View.resolveSize(resultHeight, heightMeasureSpec));
    }

    /**
     * 总关卡数
     *
     * @param levelNum
     */
    public void setAlLevelNum(int levelNum) {
        mAllLevelNum = levelNum;
    }

    /**
     * 已通过的关卡
     *
     * @param alClearLevel
     */
    public void setCurrentLeve(int alClearLevel) {
        mCurrentLevel = alClearLevel;
    }


    private String TAG = "LevelView";
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = mNormalChildViews.size();

        Log.i(TAG, "onLayout: " + getWidth() +"   " +getHeight() );
        //圆心坐标
        float[] circleCentre = {mScreenWidth / 2 * 1.0f, getMeasuredHeight() - 50};
        //每个占多少个弧度
        double oItem = 180 / (cCount - 1);
        //cCount个坐标
        float[] xyPosition;
        for (int i = cCount - 1; i >= 0; i--) {
            xyPosition = getXYPoint(circleCentre, getMeasuredWidth() / 2 - 100, -oItem * (i));
            //x坐标
            int xLabel = (int) xyPosition[0];
            //y坐标
            int yLabel = (int) xyPosition[1];

            View view = mNormalChildViews.get(i);
            view.layout((int) (xLabel - view.getMeasuredWidth() / 2 * 1.0f),
                    (int) (yLabel - view.getMeasuredHeight() / 2 * 1.0f),
                    (int) (xLabel + view.getMeasuredWidth() / 2 * 1.0f),
                    (int) (yLabel + view.getMeasuredHeight() / 2 * 1.0f));
        }

//        float[] centerBoxpoint = {mScreenWidth / 2 ,};

        int centerBoxLeftPoint =(mScreenWidth / 2  - mCenterBoxView.getMeasuredWidth() / 2);
        int centerBoxTopPoint = (int) (circleCentre[1] - mCenterBoxView.getMeasuredHeight());
        mCenterBoxView.layout(centerBoxLeftPoint,centerBoxTopPoint,
                centerBoxLeftPoint + mCenterBoxView.getMeasuredWidth(),centerBoxTopPoint + mCenterBoxView.getMeasuredHeight());
    }

    /**
     * 计算每个item的横纵坐标
     *
     * @param centrePoint 圆心坐标
     * @param radius      半径
     * @param o           所占弧度
     * @return
     */
    public float[] getXYPoint(float[] centrePoint, int radius, double o) {
        float x = (float) (radius * Math.cos(o * Math.PI / 180) + centrePoint[0]);
        float y = (float) (radius * Math.sin(o * Math.PI / 180) + centrePoint[1]);

        float[] xyPoint = {x, y};
        return xyPoint;
    }

    private List<View> mNormalChildViews;
    private View mCenterBoxView;
    @Override
    public void addView(View child) {
        if(child.getTag() ==  null){
            if(mNormalChildViews == null){
                mNormalChildViews = new ArrayList<>();
            }
            mNormalChildViews.add(child);
        }else {
            mCenterBoxView = child;
        }
        super.addView(child);
    }
}
