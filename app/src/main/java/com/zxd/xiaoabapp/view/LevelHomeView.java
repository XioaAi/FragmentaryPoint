package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zxd.xiaoabapp.LevelHomeStatusBean;
import com.zxd.xiaoabapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @ProjectName: CloudLearning_Chinese_Android
 * @Package: com.xes.chinese.banxue.view.weight LevelHomeView
 * @CreateDate: 2019/4/11 11:12
 * @Version: 1.0
 * @Author: wangziheng
 */
public class LevelHomeView extends ViewGroup {
    private int mColumns = 2;
    /**
     * 当前绘制的关卡
     */
    private int mCurPosition = 1;
    /**
     * 当前待闯关卡
     */
    private int mCurrentLvPosition = 0;
    /**
     * 线条的起止点
     */
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mStopX = 0f;
    private float mStopY = 0f;
    private List<Float[]> mLines = new ArrayList<>();
    private Paint mPaint = new Paint();

    /**
     * 待删除
     */
    private List<PointF> mPoints = new ArrayList<>();
    /**
     * 曲线路径
     */
    private Path mPath;
    /**
     * 曲线的起止控制点
     */
    private PointF mControl1, mControl2, mStartPoint, mStopPoint;
    /**
     * 路径上的圆点
     */
    private Path mLinePath;

    /**
     * 曲线圆点路径
     */
    private PathEffect mPathEffect;

    /**
     * 底部图片
     */
    private Bitmap mBottomBitmap;

    /**
     * 星星所在圆的半径
     */
    private int mLevelStarRadius ;
    /**
     * 星星数量
     */
    private int mLevelStar = 9;
    /**
     * 第一个星星的x坐标
     */
    private int mLevelStartCircleX = 100;
    /**
     *星星画笔
     */
    private Paint mLevelStarPaint;

    /**
     * 屏幕宽高
     */
    private int mScreenWidth,mScrennHeight;
    /**
     * 最后一个关卡(终极关卡的坐标)
     */
    private int mLastLevelX,mLastLevelY;
    private int mLastLevelWidth,mLastLevelHeight;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LevelHomeView(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LevelHomeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LevelHomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mControl1 = new PointF(0, 0);
        mControl2 = new PointF(0, 0);
        mStartPoint = new PointF(0, 0);
        mStopPoint = new PointF(0, 0);

        mLinePath = new Path();
        mLinePath.addCircle(0, 0, 15, Path.Direction.CCW);
        mPathEffect = new PathDashPathEffect(mLinePath, 80, 20, PathDashPathEffect.Style.ROTATE);
        mBottomBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        mLevelStarPaint = new Paint();
        mLevelStarPaint.setAntiAlias(true);
        mLevelStarPaint.setStrokeWidth(3);
        mLevelStarPaint.setColor(Color.RED);
        mLevelStarPaint.setStyle(Paint.Style.STROKE);

        mLevelStarRadius = ( mScreenWidth - 200 ) / 2;

        mCirclePath = new Path();
        mCirclePath.addArc(mLevelStartCircleX ,50,mLevelStartCircleX + mLevelStarRadius * 2,mLevelStarRadius * 2 + mLevelStartCircleX,180,180);
        mCirclePathMeasure = new PathMeasure(mCirclePath,false);

        mCirclePathLength = mCirclePathMeasure.getLength();
        mStep = mCirclePathLength / 8;
        mPos = new float[2];
        mTan = new float[2];

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxHeight = getResources().getDisplayMetrics().heightPixels;
        int maxWidth = 0;
        int childState = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            maxWidth = Math.max(maxWidth, childView.getLayoutParams().width);
            maxHeight = Math.max(maxHeight, childView.getLayoutParams().height);
            childState = View.combineMeasuredStates(childState, childView.getMeasuredState());
        }
        maxWidth += (getPaddingLeft() + getPaddingRight());
        maxHeight += (getPaddingBottom() + getPaddingTop());

        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                View.resolveSizeAndState((int) (maxHeight * 2.5 + mScrennHeight / 2), heightMeasureSpec, childState));
    }

    private String TAG = "Java转义";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mCurPosition = 1;
        mCurrentLvPosition = 0;
        mLines.clear();
        mPoints.clear();
        mStartX = 0;
        mStartY = 0;
        mStopY = 0;
        mStopX = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int x = getX(childView);
            int y = getY(i, childView);
            mStartX = x;
            mStartX = x + childView.getMeasuredWidth() / 2;
            mStartY = y + childView.getMeasuredHeight();
            PointF pointF = new PointF(mStartX, mStartY);
            mPoints.add(pointF);
            if (mStopX != 0f && mStopY != 0f) {
                Float[] floats = {mStartX, mStartY, mStopX, mStopY};
                mLines.add(floats);
            }
            mStopX = x + childView.getMeasuredWidth() / 2;
            mStopY = y;
            Log.i(TAG, "onLayout: " + x + "  " + y);

            LevelHomeStatusBean levelStatusBean = (LevelHomeStatusBean) childView.getTag();
            int endY = 0;
            int startY = 0;
            if(levelStatusBean.isVIPLevel()){
                endY = y + childView.getMeasuredHeight() / 2;
                startY = y - childView.getMeasuredHeight() / 2;
                mLastLevelY = startY ;
                mLastLevelWidth = childView.getMeasuredWidth();
                mLastLevelHeight = childView.getMeasuredHeight();
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
                int leftPointX = marginLayoutParams.leftMargin + getPaddingStart();
                int topPointY =   marginLayoutParams.topMargin + getPaddingTop();
                Log.i(TAG, "onLayout: " + leftPointX + "  " +topPointY + "  " + childView.getMeasuredHeight());
                childView.layout(leftPointX,
                        topPointY,
                        childView.getMeasuredWidth(),
                        topPointY + childView.getMeasuredHeight());
            }else {
                endY = y + childView.getMeasuredHeight();
                startY = y;
                childView.layout(x, startY, x + childView.getMeasuredWidth(), endY);
            }
        }
    }

    private int getX(View view) {
        int width = view.getMeasuredWidth();
        int firstColumnX = getResources().getDisplayMetrics().widthPixels / (mColumns);
        int tmp = firstColumnX / 2;
        int x = 0;
        int type = mCurPosition % mColumns;
        LevelHomeStatusBean levelStatusBean = (LevelHomeStatusBean) view.getTag();
        if (levelStatusBean.isCurrentLevel()) {
            mCurrentLvPosition = levelStatusBean.getCurrentLevelIndex();
        }
        if (levelStatusBean.isVIPLevel()) {
            x = firstColumnX - width / 2;
            mLastLevelX = x;
            return x;
        }
        mCurPosition++;
        if (type == 1) {
            // 靠右
            x = firstColumnX + tmp - width / 2;
            return x;
        } else {
            x = tmp - width / 2;
            // 靠左
            return x;
        }
    }

    private int getY(int index, View view) {
        int height = view.getMeasuredHeight();
        int totalHeight = getMeasuredHeight() - mScrennHeight / 2 - getPaddingBottom() - getPaddingTop();
        int perHeight = totalHeight / getChildCount();
        return perHeight * (getChildCount() - index) + getPaddingTop() - height + 300;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mLines.size(); i++) {
            mControl1.x = mLines.get(i)[0] + 100;
            mControl1.y = mLines.get(i)[1] + 100;
            mControl2.x = mLines.get(i)[2] - 100;
            mControl2.y = mLines.get(i)[3] - 100;
            mStartPoint.x = mLines.get(i)[0];
            mStartPoint.x = mLines.get(i)[1];
            mStopPoint.x = mLines.get(i)[2];
            mStopPoint.y = mLines.get(i)[3];
            mPath.reset();
            mPath.moveTo(mLines.get(i)[0], mLines.get(i)[1]);
            mPath.cubicTo(mControl1.x, mControl1.y, mControl2.x, mControl2.y, mLines.get(i)[2], mLines.get(i)[3]);
            mPaint.setPathEffect(mPathEffect);
            if (i >= mCurrentLvPosition) {
                mPaint.setColor(Color.GRAY);
            } else {
                mPaint.setColor(Color.GREEN);
            }
            canvas.drawPath(mPath, mPaint);
        }
        canvas.drawBitmap(mBottomBitmap,0,getMeasuredHeight() - mScrennHeight / 3 ,mPaint);
//        canvas.save();
//        for(int i = 0 ; i < mLevelStar ; i ++ ){
//            if (i >= mCurrentLvPosition) {
//                mLevelStarPaint.setColor(Color.GRAY);
//            } else {
//                mLevelStarPaint.setColor(Color.GREEN);
//            }
//            canvas.drawCircle(mLevelStartCircleX  + 50,mLastLevelY ,30,mLevelStarPaint);
//            canvas.rotate(22,mLastLevelX + mLastLevelWidth / 2,mLastLevelY);
//        }
//        canvas.restore();
//        Path pathistance = 0;
//        for(int i = 0 ; i < mLevelStar ; i ++){
//            mCirclePathMeasure.getPosTan(mDistance, mPos, mTan);
//            float x = mPos[0],y = mPos[1];
//            canvas.drawCircle(x,y,10,mLevelStarPaint);
//            mDistance += mStep;
//        }
//        Log.i(TAG, "onDraw: " + mDistance + "  length = " + mCirclePathLength);
//        if (mDistance < mCirclePathLength) {
//            mCirclePathMeasure.getPosTan(mDistance, mPos, mTan);
//            float x = mPos[0],y = mPos[1];
//            canvas.drawCircle(x,y,10,mLevelStarPaint);
////            mMatrix.reset();
////            mMatrix.postTranslate(-mLevelStartCircleX + 50, -mLastLevelY);
////            float degrees = (float) (Math.atan2(mTan[1], mTan[0]) * 180.0 / Math.PI);
////            mMatrix.postRotate(degrees);
////            mMatrix.postTranslate(mPos[0], mPos[1]);
////            canvas.drawCircle();
//////            canvas.drawBitmap(mBitmap, mMatrix, null);
//            mDistance += mStep;
//            invalidate();
//        } else {
////            canvas.drawBitmap(mBitmap, mMatrix, null);
//        }
    }

    Path mCirclePath;
    PathMeasure mCirclePathMeasure;
    float mStep,mDistance,mCirclePathLength;
    float[] mPos,mTan;

    @Override
    protected ChsGuideLayout.LayoutParams generateDefaultLayoutParams() {
        return new ChsGuideLayout.LayoutParams(ChsGuideLayout.LayoutParams.MATCH_PARENT, ChsGuideLayout.LayoutParams.MATCH_PARENT);
    }


    @Override
    public ChsGuideLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ChsGuideLayout.LayoutParams(getContext(),attrs);
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
