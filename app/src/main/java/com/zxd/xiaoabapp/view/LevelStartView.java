package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.zxd.xiaoabapp.utils.DensityUtils;


public class LevelStartView extends ViewGroup {

    public LevelStartView(Context context) {
        this(context,null);
    }

    public LevelStartView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LevelStartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        radius = DensityUtils.dip2px(context,70);
        Log.i(TAG, "radius = " + radius);

    }

    private final String TAG = "LevelStartView";

    private int radius;

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(),attrs);
    }

    /**
     * 计算所有ChildView的宽度和高度，然后根据ChildView的计算结果设置自己的宽度和高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算出所有childView的宽和高，（通过ViewGroup的measureChildren方法为其所有的孩子设置宽和高，此行执行完成后，childView的宽和高都已经正确的计算过了）
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        /**
         * ViewGroup内子控件的宽度和高度
         */
        int widthContent = 0;
        int heightContent = 0;

        int itemHeight =getChildAt(0).getMeasuredHeight();//单个childView的高度

        heightContent = (itemHeight+radius)*2;
        widthContent = (itemHeight+radius)*2;
        Log.d(TAG + "onMeasure", "heightContent:"+heightContent);

        /**
         * 测量ViewGroup的宽高，如果为wrap_content就按照内容计算得到的宽高
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : widthContent, (heightMode == MeasureSpec.EXACTLY) ? heightSize : heightContent);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        drawInHorizontal();
        drawInCircle();

    }

    /**
     * 按照Circle的方式排列
     */
    private void drawInCircle() {
        int cCount = getChildCount();
        int lastW = 0;

        //圆心坐标
        float[] circleCentre = {getWidth()/2*1.0f, getHeight()/2*1.0f};
        //每个占多少个弧度
        double oItem = 180/(cCount-1);
        //cCount个坐标
        float[][] xyPosition = new float[cCount][2];
        for (int i=0; i<cCount; i++){
            xyPosition[i] = getXYPoint(circleCentre,radius,-oItem*(i));
            //x坐标
            int xLabel = (int) xyPosition[i][0];
            //y坐标
            int yLabel = (int) xyPosition[i][1];

            Log.i(TAG, "position : (" + xLabel + "," + yLabel + ")");

            View view = getChildAt(i);
            view.layout((int) (xLabel - view.getMeasuredWidth() / 2 * 1.0f), (int) (yLabel - view.getMeasuredHeight() / 2 * 1.0f), (int) (xLabel + view.getMeasuredWidth() / 2 * 1.0f), (int) (yLabel + view.getMeasuredHeight() / 2 * 1.0f));


        }

    }

    /**
     * 计算每个item的横纵坐标
     * @param centrePoint 圆心坐标
     * @param radius  半径
     * @param o  所占弧度
     * @return
     */
    public float[] getXYPoint(float[] centrePoint, int radius, double o){
        Log.i(TAG,"o: "+o);
        Log.i(TAG,"radius: "+radius);
        Log.i(TAG,"centrePoint: ["+centrePoint[0]+","+centrePoint[1]+"]");

        /*x1   =   x0   +   r   *   cos(ao   *   3.14   /180   ) 
        y1   =   y0   +   r   *   sin(ao   *   3.14   /180   ) */

        float x = (float) (radius*Math.cos(o * Math.PI /180) + centrePoint[0]);
        float y = (float) (radius*Math.sin(o * Math.PI /180) + centrePoint[1]);

        float[] xyPoint = {x,y};
        return xyPoint;
    }

    public void setAlLevelNum(int i) {
    }

    public void setCurrentLeve(int mCurrentLevelIndex) {
    }
}
