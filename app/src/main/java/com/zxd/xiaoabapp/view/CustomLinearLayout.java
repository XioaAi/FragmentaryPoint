package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zxd.xiaoabapp.R;

public class CustomLinearLayout extends ViewGroup {

    private int defaultWidth = 0;
    private int defaultHeight = 0;

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_viewgroup);
        defaultWidth = typedArray.getDimensionPixelSize(R.styleable.custom_viewgroup_default_width , 300);
        defaultHeight = typedArray.getDimensionPixelSize(R.styleable.custom_viewgroup_default_height,300);
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*int width = getViewSize(defaultWidth,widthMeasureSpec);
        int height = getViewSize(defaultHeight,heightMeasureSpec);

        if(width<height){
            height = width;
        }else{
            width = height;
        }

        setMeasuredDimension(width,height);*/

        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        if(childCount == 0){
            setMeasuredDimension(0,0);
        }else{
            //如果宽高都是包裹内容
            if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
                setMeasuredDimension(getMaxChildWidth(),getTotalHeight());
            }else if(heightMode == MeasureSpec.AT_MOST){
                setMeasuredDimension(widthSize,getTotalHeight());
            }else if(widthMode == MeasureSpec.AT_MOST){
                setMeasuredDimension(getMaxChildWidth(),heightSize);
            }
        }

    }

    private int getMaxChildWidth(){
        int childCount = getChildCount();
        int maxWidth = 0;
        for(int a = 0;a<childCount;a++){
            View childView = getChildAt(a);
            int width = childView.getMeasuredWidth();
            if(width>maxWidth){
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    private int getTotalHeight(){
        int childCount = getChildCount();
        int totalHeight = 0;
        for(int a = 0;a<childCount;a++){
            View childView = getChildAt(a);
            int height = childView.getMeasuredHeight();
            totalHeight+=height;
        }
        return totalHeight;
    }

    private int getViewSize(int defaultSize,int measureSpec){
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.UNSPECIFIED://如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            case MeasureSpec.AT_MOST://如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            case MeasureSpec.EXACTLY://如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
        }

        return size;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //int left, int top, int right, int bottom
        int childCount = getChildCount();
        //记录当前的高度位置
        int curHeight = t;

        for(int a = 0;a<childCount;a++){
            View childView = getChildAt(a);
            int childViewWidth = childView.getMeasuredWidth();
            int childViewHeight = childView.getMeasuredHeight();
            childView.layout(l,curHeight,l+childViewWidth,curHeight+childViewHeight);
            curHeight += childViewHeight;
        }

    }
}
