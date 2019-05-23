package com.zxd.xiaoabapp.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.zxd.xiaoabapp.R;

import java.util.HashMap;

public final class ReelView extends View {
    private Context context;
    private Paint paint;
    private int dis;
    private int textColor;
    private int reelColor;
    private int paperColor;
    private float textSize;
    private String text;
    private float reelWidth;
    private int duration;
    private ValueAnimator disAnimator;
    private boolean isExpand;
    private float reelTopBarHeight;
    private float lineOffset;
    private HashMap _$_findViewCache;

    public ReelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.dis = 0;//每个卷轴到中心的距离
        this.textColor = -16777216;
        this.reelColor = -65536;
        this.paperColor = -1;
        this.textSize = 20.0F;
        this.text = "";
        this.reelWidth = 40.0F;//卷轴柄宽度
        this.duration = 3000;
        this.reelTopBarHeight = 20.0F;//卷轴柄上端小木块的高度
        this.lineOffset = 10.0F;//纸上分割线的距离
        if (attrs != null) {
            this.parseAttrs(attrs);
        }

        this.initPaint();
        this.initAnimator();
    }

    public ReelView(Context context) {
        this(context, (AttributeSet) null);
    }

    private final void initPaint() {
        paint = new Paint(1);
        paint.setTextSize(30.0F);
        paint.setStyle(Style.FILL);
    }

    private final void initAnimator() {
        disAnimator = ValueAnimator.ofInt(new int[]{0, this.duration / 1000});
        disAnimator.setDuration((long) this.duration);
        disAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        disAnimator.addUpdateListener((new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator it) {
                ReelView reelView = ReelView.this;
                reelView.dis = (int) (it.getAnimatedFraction() * ((float) (ReelView.this.getWidth() / 2) - ReelView.this.reelWidth));
                ReelView.this.postInvalidate();
            }
        }));
    }

    private final void parseAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReelView);
        textColor = typedArray.getColor(R.styleable.ReelView_textColor, Color.BLACK);
        reelColor = typedArray.getColor(R.styleable.ReelView_reelColor, Color.RED);
        paperColor = typedArray.getColor(R.styleable.ReelView_paperColor, Color.WHITE);
        text = typedArray.getString(R.styleable.ReelView_text);
        reelWidth = typedArray.getDimension(R.styleable.ReelView_reelWidth, 40f);
        duration = typedArray.getInteger(R.styleable.ReelView_duration, 3000);
        textSize = typedArray.getDimension(R.styleable.ReelView_textSize, 20f);
        typedArray.recycle();
    }

    protected void onDraw(@Nullable Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            int count = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);//设置离屏缓冲，不设置的话会有问题
            paint.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
            drawText(canvas);
            drawPaper(canvas);
            paint.setXfermode( null);
            drawPaerLines(canvas);
            canvas.restoreToCount(count);
            drawReels(canvas);//绘制卷轴柄
        }
    }

    private final void drawText(Canvas canvas) {
        this.textSize = Math.min(this.textSize, (float) this.getHeight() - reelTopBarHeight * 2 - lineOffset*2);
        paint.setFakeBoldText(true);
        paint.setTextSize(textSize);
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        Rect rect = new Rect();
        paint.getTextBounds(this.text, 0, text.length(), rect);
        canvas.drawText(text, centerX - rect.width() / 2, centerY + rect.height() / 2, paint);
    }

    private final void drawPaper(Canvas canvas) {
        float centerX = getWidth() / 2;
        Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Config.ARGB_8888);
        Canvas canvasTmp = new Canvas(bitmap);
        paint.setColor(this.paperColor);
        canvasTmp.drawRect(centerX - dis, reelTopBarHeight, centerX + dis, getHeight()-reelTopBarHeight, paint);
        paint.setColor(-16777216);
        paint.setStrokeWidth(2.0F);
        canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
    }

    private final void drawPaerLines(Canvas canvas) {
        float centerX = getWidth() /  2;
        canvas.drawLine(centerX-dis, reelTopBarHeight+lineOffset, centerX+dis, reelTopBarHeight+lineOffset, paint);
        canvas.drawLine(centerX-dis, getHeight()-(reelTopBarHeight+lineOffset),
                centerX+dis, getHeight()-(reelTopBarHeight+lineOffset), paint);
    }

    private final void drawReels(Canvas canvas) {
        this.drawLeftReel(canvas);
        this.drawRightReel(canvas);
    }

    private final void drawLeftReel(Canvas canvas) {
        float centerX = getWidth() / 2;
        float left = centerX - dis - reelWidth;
        float right = centerX - dis;
        drawReel(canvas, left, right);
    }

    private final void drawRightReel(Canvas canvas) {
        float centerX = (float) this.getWidth() / (float) 2;
        float left = centerX + (float) this.dis;
        float right = left + this.reelWidth;
        drawReel(canvas, left, right);
    }

    private final void drawReel(Canvas canvas, float left, float right) {
        paint.setColor(this.reelColor);
        canvas.drawRect(left, reelTopBarHeight, right, getHeight()-reelTopBarHeight, paint);
        //绘制小木块
        canvas.drawRect(left + reelWidth/4, 0.0F, right - reelWidth/4, reelTopBarHeight, paint);
        canvas.drawRect(left +reelWidth /4, getHeight()-reelTopBarHeight, reelWidth/4, getHeight(), paint);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disAnimator.cancel();
    }

    public boolean onTouchEvent(@Nullable MotionEvent event) {
        Integer var2 = event != null ? event.getAction() : null;
        boolean var3 = false;
        if (var2 != null) {
            if (var2 == 0) {
                if (!disAnimator.isRunning()) {
                    if (!isExpand) {
                        float centerX = (float) this.getWidth() / (float) 2;
                        if (event.getX() >= centerX - this.reelWidth && event.getX() <= centerX + this.reelWidth) {
                            startAnimator();
                            isExpand = true;
                        }
                    } else {
                        disAnimator.reverse();
                        isExpand = false;
                    }
                }

                return true;
            }
        }

        return false;
    }

    private final void startAnimator() {
        if (!disAnimator.isStarted()) {
            disAnimator.start();
        }
    }

    // $FF: synthetic method
    public static final int access$getDis$p(ReelView $this) {
        return $this.dis;
    }

    // $FF: synthetic method
    public static final void access$setReelWidth$p(ReelView $this, float var1) {
        $this.reelWidth = var1;
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View) this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
}
