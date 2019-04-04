package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomProgress extends View {

    final float radius = 160f;

    RectF rectF = new RectF();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float progress = 0;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    {
        paint.setTextSize(28);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getMeasuredWidth()/2;
        float centerY = getMeasuredHeight()/2;

        paint.setColor(Color.parseColor("#E91E63"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        paint.setStrokeCap(Paint.Cap.ROUND);
        rectF.set(centerX-radius,centerY-radius,centerY+radius,centerY+radius);
        canvas.drawArc(rectF,135,progress*2.7f,true,paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText((int)progress+"%",centerX,centerY,paint);


    }
}
