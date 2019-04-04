package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.zxd.xiaoabapp.R;

public class CustomHorizationProgress extends ProgressBar {

    private int textSize = 24;
    private int reachHeight = 10;
    private int reachColor = 0xFFEF4F4F;
    private int unReachColor = 0xFF14ABFE;
    private int textColor = 0xFFFC00D1;
    protected int mUnReachHeight = 4;

    private Paint paint;
    private int mRealWidth;

    public CustomHorizationProgress(Context context) {
        this(context,null);
    }

    public CustomHorizationProgress(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomHorizationProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initOptions(context,attrs);

    }

    private void initOptions(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.horization_progress_view);
        reachHeight = typedArray.getDimensionPixelSize(R.styleable.horization_progress_view_reach_height,reachHeight);
        textSize = typedArray.getDimensionPixelSize(R.styleable.horization_progress_view_text_size,textSize);
        reachColor = typedArray.getColor(R.styleable.horization_progress_view_reach_color,reachColor);
        unReachColor = typedArray.getColor(R.styleable.horization_progress_view_unreach_color,unReachColor);
        textColor = typedArray.getColor(R.styleable.horization_progress_view_text_color,textColor);
        typedArray.recycle();

        paint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthval = MeasureSpec.getSize(widthMeasureSpec);
        int heightval ;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(heightMode == MeasureSpec.EXACTLY){
            heightval = heightSize;
        }else{
            int textHeight = (int) (paint.descent()-paint.ascent());
            heightval = getPaddingTop() + getBottom() + Math.max(reachHeight,textHeight);

            if(heightMode == MeasureSpec.AT_MOST){
                heightval = Math.min(heightval,heightSize);
            }
        }

        setMeasuredDimension(widthval,heightval);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);

        //判断需要绘制右面进度
        boolean noNeedUnReach = false;

        String text = getProgress()+"%";
        int textWidth = (int) paint.measureText(text);

        //绘制百分比
        float radio = getProgress()*1.0f/getMax();
        //进度所占的长度
        float progressX = radio*mRealWidth;
        if(progressX + textWidth > mRealWidth){
            progressX = mRealWidth - textWidth;
            noNeedUnReach = true;
        }
        //绘制结束位置X
        paint.setColor(reachColor);
        paint.setStrokeWidth(reachHeight);
        canvas.drawLine(0,0,progressX,0,paint);

        //绘制字体
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        int textHeight = (int) (-(paint.descent()+paint.ascent()));
        canvas.drawText(text,progressX,textHeight/2,paint);

        if(!noNeedUnReach){
            //右面进度条起点
            paint.setColor(unReachColor);
            paint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(progressX+textWidth,0,mRealWidth,0,paint);
        }

        canvas.restore();

    }
}
