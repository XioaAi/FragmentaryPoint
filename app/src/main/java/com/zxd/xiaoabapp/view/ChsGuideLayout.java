package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxd.xiaoabapp.R;


public class ChsGuideLayout extends ViewGroup {

    /**
     * 蒙层父view
     */
    private View mParentView;
    /**
     * 蒙层内置子 view
     */
    private View mChildView;
    /**
     * 高亮显示的view
     */
    private View mTargetView;
    private Paint mLightPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Bitmap mQbitmap;
    public ChsGuideLayout(Context context) {
        this(context,null);
    }

    public ChsGuideLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChsGuideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mScreenWidth,mScrennHeight;
    private void init() {

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            display.getRealSize(outPoint);
        } else {
            display.getSize(outPoint);
        }

        mScrennHeight = outPoint.y;
        mScreenWidth = outPoint.x;
        setWillNotDraw(false);
        mLightPaint = new Paint();
        mLightPaint.setAntiAlias(true);
        mLightPaint.setColor(Color.parseColor("#000000"));
        mBitmap = Bitmap.createBitmap(mScreenWidth,mScrennHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.parseColor("#50000000"));

        mQbitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        mSrcRect = new Rect(0,0,mQbitmap.getWidth(),mQbitmap.getHeight());
    }
    private String TAG = "guideLayout";
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mParentView != null || getParent() != null){
            if(mParentView == null){
                mParentView = (View) getParent();
            }
            if(mChildView != null){
                mChildView.measure(
                        getDefaultMeasure(mChildView.getLayoutParams().width,mScreenWidth),
                        getDefaultMeasure(mChildView.getLayoutParams().height,mScrennHeight));
            }
            if(mBirdView != null){
                mBirdView.measure(
                        getDefaultMeasure(mBirdView.getLayoutParams().width,mScreenWidth),
                        getDefaultMeasure(mBirdView.getLayoutParams().height,mScrennHeight));

            }
            setMeasuredDimension(mScreenWidth,mScrennHeight);
        }
    }

    private int getDefaultMeasure(int childSize,int defaultSize) {
        int childResultSize;
        int childResultModel;
        if(childSize >= 0){
            childResultSize = childSize;
            childResultModel = MeasureSpec.EXACTLY;
        }else if(childSize == LayoutParams.MATCH_PARENT){
            childResultSize = defaultSize;
            childResultModel = MeasureSpec.AT_MOST;
        }else {
            childResultSize = defaultSize;
            childResultModel = MeasureSpec.AT_MOST;
        }
//        Log.i(TAG, "getDefaultMeasure: " + childResultSize);
        return MeasureSpec.makeMeasureSpec(childResultSize,childResultModel);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int mChildviewTop = 0;
        if(mChildView != null && mTargetLocation.length > 1){
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) mChildView.getLayoutParams();
            int left = (getMeasuredWidth() - mChildView.getMeasuredWidth()) / 2 + getPaddingLeft() +  marginLayoutParams.leftMargin;
            int top = mChildviewTop = (getMeasuredHeight() - mChildView.getMeasuredHeight()) / 2 + getPaddingTop() + marginLayoutParams.topMargin ;
            int right = mIconRight = left + mChildView.getMeasuredWidth() - getPaddingRight() - marginLayoutParams.rightMargin;
            int bottom = top + mChildView.getMeasuredHeight() - getPaddingBottom() - marginLayoutParams.bottomMargin;
            mChildView.layout(left,top,right,bottom);
        }
        if(mBirdView != null && mTargetLocation.length > 1 && mTargetView != null){
            int left = mTargetLocation[0] + mTargetView.getMeasuredWidth() / 2;
            int top =   mTargetLocation[1] + mTargetView.getMeasuredHeight() / 2;
            mBirdView.layout(left,top,mIconRight,mChildviewTop + 200);
        }
    }

    private int mIconLeft,mIconTop,mIconRight,mIconBottom;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mTargetView != null){
            int[] targetLocation = new int[2];
            mTargetView.getLocationOnScreen(targetLocation);
            RectF rectF = new RectF();
            rectF.left = mIconLeft = targetLocation[0] - 10;
            rectF.top = mIconTop =targetLocation[1] - 10;
            rectF.right = targetLocation[0] + 10 + mTargetView.getWidth();
            rectF.bottom = targetLocation[1] + 10 + mTargetView.getHeight();
            mLightPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mCanvas.drawRoundRect(rectF,20,20,mLightPaint);
        }
        canvas.drawBitmap(mBitmap,0,0,null);
//        canvas.save();
//        if(mChildView != null){
//            TextView hellowTv = mChildView.findViewById(R.id.chsbx_level_second_guide_dialog_hellow);
//            int[] iconLocation = new int[2];
//            hellowTv.getLocationOnScreen(iconLocation);
//            mIconBottom = iconLocation[1];
//        }
//        mDstRectF = new RectF(mIconLeft,mIconTop,mIconRight,mIconBottom);
//        canvas.drawBitmap(mQbitmap,mSrcRect,mDstRectF,mLightPaint);
    }

    private RectF mDstRectF;
    private Rect mSrcRect;
    public void setChildView(View childView){
        mChildView = childView;
        addView(childView);
        invalidate();
    }
    private ImageView mBirdView;
    public void setBirdView(ImageView birdView){
        mBirdView = birdView;
        addView(birdView);
        invalidate();
    }

    private int[] mTargetLocation = new int[2];
    public void setParentView(ViewGroup parentView){
        mParentView = parentView;
        ((ViewGroup) mParentView).addView(this);
    }
    public void setTargetView(View targetView){
        mTargetView = targetView;
        mTargetView.getLocationOnScreen(mTargetLocation);
        requestLayout();
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    public static class LayoutParams extends MarginLayoutParams{

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
