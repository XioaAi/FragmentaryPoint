package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zxd.xiaoabapp.R;

public class LineBitmapView extends View {

    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private Context mContext;

    private int bitmapWidth ;
    private int bitmapHeight ;
    private int bitmapLeft1 = 200;//Bitmap与两面的边距
    private int bitmapLeft ; //第一个Bitmap的左边距
    private int bitmapLeft2 ; //第二个Bitmap的左边距
    private int bitmapTop ;

    private Bitmap bitmap;

    public LineBitmapView(Context context) {
        this(context,null);
    }

    public LineBitmapView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LineBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.blue));


        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_round);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bitmapLeft = mWidth - bitmapLeft1 - bitmapWidth;
        bitmapLeft2 = bitmapLeft1;
        bitmapTop = 100;

        Path path = new Path();
        path.addCircle(0, 0, 3, Path.Direction.CW);
        mPaint.setPathEffect(new PathDashPathEffect(path,15,0,PathDashPathEffect.Style.ROTATE));

        for(int a = 0;a<5;a++){

            canvas.drawBitmap(bitmap,bitmapLeft,bitmapTop,mPaint);

            bitmapTop+=300;

            canvas.drawBitmap(bitmap,bitmapLeft2,bitmapTop,mPaint);

            path.reset();
            path.moveTo(bitmapLeft+bitmapWidth/2,bitmapTop-300+bitmapHeight);
            path.cubicTo(bitmapLeft+bitmapWidth/2-30,bitmapTop-300+bitmapHeight+100,
                    bitmapLeft2+bitmapWidth/2+30,bitmapTop-100,
                    bitmapLeft2+bitmapWidth/2,bitmapTop);
            canvas.drawPath(path,mPaint);

            if(a < 5){
                path.reset();
                path.moveTo(bitmapLeft2+bitmapWidth/2,bitmapTop+bitmapHeight);
                path.cubicTo(bitmapLeft2+bitmapWidth/2+30,bitmapTop+bitmapHeight+100,
                        bitmapLeft+bitmapWidth/2-30,bitmapTop+300-100,
                        bitmapLeft+bitmapWidth/2,bitmapTop+300);
                canvas.drawPath(path,mPaint);
            }

            bitmapTop+=300;

        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else{
            mHeight = 100+600*5;
        }

        setMeasuredDimension(mWidth, mHeight);

    }
}
