package com.zxd.xiaoabapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class LitePageOfAnim extends ViewGroup {

    private float minScale = 0.8f;
    private float maxScale = 1f;

    private int margeLeft = 40;

    private float mLastX = 0,mLastY = 0;//上次触摸点
    private int mTouchSlop;//触发滑动的最小距离
    private boolean isMove = false;
    private float mOffsetX ;//水平偏移量
    private float mOffsetPercent = 0;//水平便宜比例
    private boolean isReordered;//是否已经交换过层级
    private ValueAnimator mAnimator;

    public LitePageOfAnim(Context context) {
        this(context,null,0);
    }

    public LitePageOfAnim(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LitePageOfAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0,height = 0;

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            for(int a = 0;a<getChildCount();a++){
                View view = getChildAt(0);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                width += view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
        }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            for(int a = 0;a<getChildCount();a++){
                View view = getChildAt(0);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                height += view.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            }
        }

        setMeasuredDimension(width,height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int a = 0;a<getChildCount();a++){
            View view = getChildAt(a);

            int baseLine = getBaselineWithChild(a);

            LayoutParams lp = (LayoutParams) view.getLayoutParams();

            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();

            int left = baseLine-viewWidth/2 + lp.leftMargin - margeLeft/2;
            int top = getHeight()/2-viewHeight/2;
            int bottom = getHeight()/2+viewHeight/2;
            int right = baseLine+viewWidth/2 + margeLeft;

            view.setScaleY(lp.scale);

            view.layout(left,top,right,bottom);
        }
    }

    public int getBaselineWithChild(int index){

        int baseline = getWidth()/2;

        View view  = getChildAt(index);

        int baseLine = 0;

        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        if(lp.from == 0){
            if(lp.to == 0){
                baseLine = baseline;
            }else{
                if(mOffsetPercent>=1){
                    float count = mOffsetPercent/100;
                    for(float a = count;a<=0;a-=0.01){
                        baseLine = (int) (baseline - margeLeft * a);
                    }
                }else{
                    baseLine = (int) (baseline + margeLeft * mOffsetPercent);
                }
            }
        }else{
            if(lp.to == 0){
                if(mOffsetPercent>=1){
                    float count = mOffsetPercent/100;
                    for(float a = count;a<=0;a-=0.01){
                        baseLine = (int) (baseline - ((margeLeft + view.getMeasuredWidth()) * a));
                    }

                }else{
                    baseLine = (int) (baseline + ((margeLeft + view.getMeasuredWidth()) * mOffsetPercent));
                }
            }else{
                baseLine = baseline;
            }
        }

        return baseLine;
    }

    /**
     * 主要用于判断是否拦截点击事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = x-mLastX;
                float offsetY = y-mLastY;

                if(Math.abs(offsetX)>mTouchSlop || Math.abs(offsetY)>mTouchSlop){
                    mLastX = x;
                    mLastY = y;
                    isMove = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                handleActionUp(x,y);
                break;
        }

        return isMove;
    }

    /**
     * 处理滑动事件
     * 来变换子View
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float offsetX = x-mLastX;

                Log.i("XiaoAi",x +"----"+mLastX);

                mOffsetX += offsetX;
                moveItem();
                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                handleActionUp(x,y);
                break;

        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    /**
     * 添加动画
     * @param x
     * @param y
     */
    private void handleActionUp(float x, float y) {
        playFixAnimation();
    }

    /**
     * 开启动画
     */
    private void playFixAnimation() {
        if(getChildCount() == 0){
            return;
        }

        //起始点  就是当前的滑动距离
        float start = mOffsetX ;
        float end ;

        //如果滑动的距离超过了宽度的一半，那么就顺势往那边走下去
        //如果滑动百分比为正数，表示向右滑动了》50%  所以目的地就是宽度
        if(mOffsetPercent > 0.5){
            end = getWidth();
        }else if(mOffsetPercent < -0.5){
            end = -getWidth();
        }else{//如果滑动没有超过0.5   则自动回弹
            end = 0;
        }

        startValueAnimator(start,end);
    }

    private void startValueAnimator(float start, float end) {
        if(start == end){
            return;
        }

        if (mAnimator!=null && mAnimator.isRunning()){
            mAnimator.cancel();
        }

        mAnimator = ValueAnimator.ofFloat(start,end).setDuration(400);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                mOffsetX = currentValue;
                moveItem();
            }
        });
        mAnimator.start();

    }

    /**
     * 更新子View的出发点和目的地
     * 更新子View的层级
     * 更新子View的缩放大小
     */
    private void moveItem(){
        mOffsetPercent = mOffsetX/getMeasuredWidth();
        Log.i("XiaoAi",mOffsetX +"----"+getMeasuredWidth()+"----"+mOffsetPercent);
        updateChildFromAndTo();
        updateChildrenOrder();
        updateChildrenAlphaAndScale();
        requestLayout();
    }

    private void updateChildrenAlphaAndScale() {
        //遍历子View
        for(int a = 0;a<getChildCount();a++){
            updateAlphaAndScale(getChildAt(a));
        }
    }

    private void updateAlphaAndScale(View child) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        switch (lp.from){
            //最下面的子View
            case 0:
                lp.scale = minScale + (1-minScale) * mOffsetPercent;
                break;
            //最上面的子View
            case 1:
                lp.scale = 1 - (1 - minScale) * mOffsetPercent;
                break;
            /*//中间的子View
            case 2:
                //这里就不用判断   因为无论向哪一边   透明度和缩放比都是减小
                lp.alpha = 1 - (1 - mMinAlpha) * Math.abs(mOffsetPercent);
                lp.scale = 1 - (1 - mMinScale) * Math.abs(mOffsetPercent);
                break;*/
        }

    }

    private void updateChildrenOrder() {
        //如果滑动距离超过ViewGroup宽度的一半，就把子View交换顺序，并标记已经交换过
        if(Math.abs(mOffsetPercent)>0.8){
            if(!isReordered){
                exchangeOrder(0,1);
                isReordered = true;
            }
        }else{
            //滑动距离没有超过一半，即有可能是滑动超过一半之后然后滑动回来
            //如果isReordered = true 就表示本次滑动已经交换过顺序  所以要在交换一次
            if(isReordered){
                exchangeOrder(0,1);
                isReordered = false;
            }

        }
    }

    private void exchangeOrder(int fromIndex, int toIndex) {
        if(fromIndex == toIndex){
            return;
        }

        View fromView = getChildAt(fromIndex);
        View toView = getChildAt(toIndex);
        //分离出来
        detachViewFromParent(fromView);
        detachViewFromParent(toView);
        //在添加回去
        attachViewToParent(fromView,
                toIndex>getChildCount()?getChildCount():toIndex,
                fromView.getLayoutParams());
        attachViewToParent(toView,
                fromIndex>getChildCount()?getChildCount():fromIndex,
                toView.getLayoutParams());
        //重新绘制
        invalidate();
    }

    private void updateChildFromAndTo() {
        //如果滑动的距离>=ViewGroup宽度
        if(Math.abs(mOffsetPercent)>=1){
            isReordered = false;
            //遍历子View，标记已经到达目的地
            for(int a = 0;a<getChildCount();a++){
                LayoutParams lp = (LayoutParams) getChildAt(a).getLayoutParams();
                lp.from = lp.to;
            }
            //处理溢出：比如宽度是100 现在是120   那么处理之后比变成20
            mOffsetX %= getWidth();
            //同理，这是个百分比
            mOffsetPercent %= 1f;
        }else{
            //遍历子View，并根据当前滑动的百分比来更新子View的目的地
            for(int a = 0;a<getChildCount();a++){
                LayoutParams lp = (LayoutParams) getChildAt(a).getLayoutParams();
                //lp.from   即为子View的索引
                if(lp.from == 0){
                    lp.to = 1;
                }else{
                    lp.to = 0;
                }
            }
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        LayoutParams lp = params instanceof LayoutParams? (LayoutParams) params :new LayoutParams(params);
        lp.from = index==-1 ? getChildCount() : index;

        if(getChildCount() >= 1){
            lp.scale = maxScale;
        }else{
            lp.scale = minScale;
        }
        super.addView(child, index, params);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    static class LayoutParams extends MarginLayoutParams{
        int from,to;
        float scale;

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
