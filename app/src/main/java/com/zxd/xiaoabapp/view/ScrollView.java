package com.zxd.xiaoabapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

public class ScrollView extends View {

    private Paint paint;

    //卷轴高度
    private float scrollHeight = 80;
    //底部卷轴到上部卷轴的高度
    private float dis = 0;
    //卷轴柄的高度
    private float reelTopBarHeight = 80;
    //纸上分割线的间隔
    private float lineOffset = 20;
    //圆角矩形多出距离
    private float roundHeight = 20;

    //动画执行时间
    private int duration = 2000;
    private ValueAnimator disAnimator;

    private String text = "老妈万福";

    //记录展开状态
    private boolean isExpand = false;


    public ScrollView(Context context) {
        this(context,null);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        paint = new Paint();
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.FILL);

        initAnimation();
    }

    private void initAnimation(){
        disAnimator = ValueAnimator.ofInt(new int[]{0,duration/1000});
        disAnimator.setDuration(duration);
        disAnimator.setInterpolator(new BounceInterpolator());
        disAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dis = animation.getAnimatedFraction()*(getHeight()-scrollHeight*2-roundHeight*2) ;
                ScrollView.this.postInvalidate();
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int count = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);//设置离屏缓冲，不设置的话会有问题
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        drawText(canvas);
        drawPager(canvas);
        paint.setXfermode( null);
        drawPaerLines(canvas);
        canvas.restoreToCount(count);
        drawReels(canvas);//绘制卷轴

    }

    /**
     * 在卷轴上画字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        paint.setFakeBoldText(true);
        paint.setTextSize(60);
        paint.setColor(Color.RED);
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,getWidth()/2-rect.width()/2,getHeight()/2-rect.height()/2,paint);
    }

    /**
     * 画卷轴上的纸
     * @param canvas
     */
    private void drawPager(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasTmp = new Canvas(bitmap);
        paint.setColor(Color.GREEN);
        canvasTmp.drawRect(reelTopBarHeight, roundHeight, getWidth()-reelTopBarHeight, scrollHeight*2+dis-roundHeight, paint);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
    }

    /**
     * 画卷轴的线
     * @param canvas
     */
    private void drawPaerLines(Canvas canvas) {
        paint.setStrokeWidth(10);
        canvas.drawLine(reelTopBarHeight+lineOffset,scrollHeight+roundHeight,
                reelTopBarHeight+lineOffset,dis+scrollHeight+roundHeight,paint);
        canvas.drawLine(getWidth()-reelTopBarHeight-lineOffset,reelTopBarHeight+roundHeight,
                getWidth()-reelTopBarHeight-lineOffset,dis+scrollHeight+roundHeight,paint);
    }

    /**
     * 绘制卷轴
     * @param canvas
     */
    private void drawReels(Canvas canvas) {
        drawTopReel(canvas);
        drawBottomReel(canvas);
    }

    private void drawTopReel(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(reelTopBarHeight, roundHeight, getWidth()-reelTopBarHeight,scrollHeight+roundHeight , paint);
        //绘制小木块
        canvas.drawRect(0, roundHeight,
                reelTopBarHeight, scrollHeight+roundHeight, paint);
        canvas.drawRect(getWidth()-reelTopBarHeight, roundHeight,
                getWidth(), scrollHeight+roundHeight, paint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(roundHeight, 0,
                    reelTopBarHeight, scrollHeight+roundHeight*2,
                    20,20,paint);
            canvas.drawRoundRect(getWidth()-reelTopBarHeight, 0,
                    getWidth()-roundHeight, scrollHeight+roundHeight*2,
                    20,20,paint);
        }
    }

    private void drawBottomReel(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(reelTopBarHeight, scrollHeight+dis+roundHeight, getWidth()-reelTopBarHeight, scrollHeight*2+dis+roundHeight, paint);
        //绘制小木块
        canvas.drawRect(0, scrollHeight+dis+roundHeight,
                reelTopBarHeight, scrollHeight*2+dis+roundHeight, paint);
        canvas.drawRect(getWidth()-reelTopBarHeight, scrollHeight+dis+roundHeight ,
                getWidth(), scrollHeight*2+dis+roundHeight , paint);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(roundHeight, scrollHeight+dis,
                    reelTopBarHeight, (2*scrollHeight+dis+roundHeight*2),
                    20,20,paint);
            canvas.drawRoundRect(getWidth()-reelTopBarHeight, scrollHeight+dis ,
                    getWidth()-roundHeight, (2*scrollHeight+dis+roundHeight*2),
                    20,20,paint);
        }
    }

    /**
     * 开始动画展开卷轴
     */
    public void startExplain(){
        if (!disAnimator.isRunning()) {
            if (!isExpand) {
                if (!disAnimator.isStarted()) {
                    disAnimator.start();
                }
                isExpand = true;
            } else {
                disAnimator.reverse();
                isExpand = false;
            }
        }
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (!disAnimator.isRunning()) {
                if (!isExpand) {
                    if (event.getY() >= 0 && event.getY() <= scrollHeight*2+100) {
                        if (!disAnimator.isStarted()) {
                            disAnimator.start();
                        }
                        isExpand = true;
                    }
                } else {
                    disAnimator.reverse();
                    isExpand = false;
                }
            }
            return true;
        }
        return false;
    }*/
}
