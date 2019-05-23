package com.zxd.xiaoabapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.zxd.xiaoabapp.R;

public class LitePager extends ViewGroup {

    private float mMinScale, mMaxScale;//最小和最大缩放比例
    private float mMinAlpha, mMaxAlpha;//最小和最大不透明度
    private boolean isBeingDragged;//是否已经开始了拖动
    private float mLastX, mLastY;//上一次的触摸坐标
    private float mDownX, mDownY;//手指按下坐标
    private int mTouchSlop;//触发滑动的最小距离
    private float mOffsetX;//水平和垂直偏移量
    private float mOffsetPercent;//偏移的百分比
    private boolean isReordered;//是否已经交换过层级顺序
    private ValueAnimator mAnimator;

    public LitePager(Context context) {
        this(context,null,0);
    }

    public LitePager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LitePager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context,attrs,defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LitePager,defStyleAttr,0);
        mMaxAlpha = typedArray.getFloat(R.styleable.LitePager_maxAlpha,1);
        mMinAlpha = typedArray.getFloat(R.styleable.LitePager_minAlpha,0.5f);
        mMinScale = typedArray.getFloat(R.styleable.LitePager_minScale,0.5f);
        mMaxScale = typedArray.getFloat(R.styleable.LitePager_maxScale,1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        int width = 0,height = 0;
        LayoutParams layoutParams;
        /*UNSPECIFIED：不对View大小做限制，如：ListView，ScrollView
        EXACTLY：确切的大小，如：100dp或者march_parent
        AT_MOST：大小不可超过某数值，如：wrap_content*/
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            for(int a = 0;a<childCount;a++){
                View view = getChildAt(a);
                layoutParams = (LayoutParams) view.getLayoutParams();
                width += view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
        }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            for(int a = 0;a<childCount;a++){
                View view = getChildAt(a);
                layoutParams = (LayoutParams) view.getLayoutParams();
                height+=view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        }

        setMeasuredDimension(width,height);

    }

    //摆放三个子View的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int a = 0;a<getChildCount();a++){
            View view = getChildAt(a);
            int baseLine = getBaseLineByChild(view);

            //根据基准线摆放子View的位置
            int childViewWidth = view.getMeasuredWidth();
            int childViewHeight = view.getMeasuredHeight();

            int left = baseLine - childViewWidth/2;
            int right = left + childViewWidth;
            int top = getHeight()/2-childViewHeight/2;
            int bottom = top + childViewHeight;
            LayoutParams lp = (LayoutParams) view.getLayoutParams();

            view.setAlpha(lp.alpha);
            view.setScaleX(lp.scale);
            view.setScaleY(lp.scale);

            view.layout(left + lp.leftMargin + getPaddingLeft(),
                    top + lp.topMargin + getPaddingTop() ,
                    right + lp.rightMargin - getPaddingRight(),
                    bottom + lp.bottomMargin - getPaddingBottom());
        }
    }

    /**
     * 根据子View在ViewGroup中的索引，计算基准线
     * @return
     */
    public int getBaseLineByChild(View childView){
        /*int width = getWidth();
        int position = indexOfChild(childView);
        if(position == 0){
            return width/4;
        }else if(position == 1){
            return width/2 + width/4 ;
        }else{
            return width/2;
        }*/
        //改成根据滑动百分比来动态计算基准线
        int baseLineLeft = getWidth()/4;
        int baseLineCenter = getWidth()/2;
        int baseLineRight = getWidth() - baseLineLeft;

        int baseLine = 0;

        LayoutParams lp = (LayoutParams) childView.getLayoutParams();

        switch (lp.from){
            //最左面的子View
            case 0:
                switch (lp.to){
                    //目的地为1   证明手指在向左滑，所以mOffsetPercent用负的
                    //当前基准线 = 初始基准线 + 与目标基准线的距离 * 滑动百分比
                    case 1:
                        baseLine = baseLineLeft + (int)((baseLineRight - baseLineLeft)*-mOffsetPercent);
                        break;
                    case 2:
                        //如果目的地为2   则目标基准线就是中间基准线
                        baseLine = baseLineLeft + (int)((baseLineCenter - baseLineLeft)*mOffsetPercent);
                        break;
                    case 0:
                        baseLine = baseLineLeft;
                        break;
                }
                break;
            case 1:
                if(lp.to == 0){
                    baseLine = baseLineRight + (int)((baseLineRight-baseLineLeft)*-mOffsetPercent);
                }else if(lp.to == 2){
                    baseLine = baseLineRight + (int)((baseLineRight-baseLineCenter)*mOffsetPercent);
                }else{
                    baseLine = baseLineRight;
                }
                break;
            case 2:
                if(lp.to == 0){
                    baseLine = baseLineCenter + (int)((baseLineCenter-baseLineLeft)*mOffsetPercent);
                }else if(lp.to == 1){
                    baseLine = baseLineCenter + (int)((baseLineRight-baseLineCenter)*mOffsetPercent);
                }else{
                    baseLine = baseLineCenter;
                }
                break;
        }
        return baseLine;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(getChildCount()>2){
            throw new IllegalStateException("不能超过三个子View");
        }
        //即第三个透明度为正常    其他两个不正常
        LayoutParams layoutParams = params instanceof LayoutParams? (LayoutParams) params :new LayoutParams(params);
        layoutParams.from = index==-1 ? getChildCount() : index;
        if(getChildCount()<2){
            layoutParams.alpha = mMinAlpha;
            layoutParams.scale = mMinScale;
        }else{
            layoutParams.alpha = 1;
            layoutParams.scale = 1;
        }

        super.addView(child, index, params);
    }

    /**
     * 判断是否有拖拽事件并拦截
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

                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = x - mLastX;
                float offsetY = y - mLastY;
                //判断是否触发拖动事件
                if(Math.abs(offsetX)>mTouchSlop || Math.abs(offsetY)>mTouchSlop){
                    mLastX = x;
                    mLastY = y;
                    isBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                return handleActionUp(x,y);

        }

        return isBeingDragged;
    }

    /**
     * 更新滑动百分比
     * 更新子View的出发点和目的地
     * 更新子View的层级
     * 更新子View的不透明度和缩放比例
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
                float offsetX = x - mLastX;
                float offsetY = y - mLastY;

                mDownY = y;
                mDownX = x;

                if(Math.abs(offsetX)>mTouchSlop || Math.abs(offsetY)>mTouchSlop){
                    mOffsetX += offsetX;
                    onItemMove();
                }
                break;
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
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
    private boolean handleActionUp(float x, float y) {

        float offsetX = x - mDownX;
        float offsetY = y - mDownY;

        //用于判断是否是点击事件
        if(Math.abs(offsetX)<mTouchSlop || Math.abs(offsetY)<mTouchSlop){
            //获取触摸点所在的View
            View view = findHitView(x,y);
            if(view!=null){
                if (indexOfChild(view) == 2) {
                    //点击第一个子view不用播放动画，直接不拦截
                    requestDisallowInterceptTouchEvent(false);
                    return false;
                } else {
                    LayoutParams lp = (LayoutParams) view.getLayoutParams();
                    setSelection(lp.from);
                    return true;
                }
            }
        }

        playFixAnimation();
        requestDisallowInterceptTouchEvent(false);
        return false;
    }

    private void setSelection(int index){
        if(getChildCount() == 0 || index == 2 ){
            return;
        }

        float start = mOffsetX;
        float end = 0;
        if(index == 0){
            end = getWidth();
        }else if(index == 1){
            end = -getWidth();
        }else{
            return;
        }
        startValueAnimator(start,end);
    }

    private View findHitView(float x,float y){
        for(int a = 0;a<getChildCount();a++){
            View view  = getChildAt(a);
            if(pointInView(view,new float[]{x,y})){
                return view;
            }
        }
        return null;
    }

    /**
     * @param view   目标view
     * @param points 坐标点(x, y)
     * @return 坐标点是否在view范围内
     */
    private boolean pointInView(View view, float[] points) {
        // 像ViewGroup那样，先对齐一下Left和Top
        points[0] -= view.getLeft();
        points[1] -= view.getTop();
        // 获取View所对应的矩阵
        Matrix matrix = view.getMatrix();
        // 如果矩阵有应用过变换
        if (!matrix.isIdentity()) {
            // 反转矩阵
            matrix.invert(matrix);
            // 映射坐标点
            matrix.mapPoints(points);
        }
        //判断坐标点是否在view范围内
        return points[0] >= 0 && points[1] >= 0 && points[0] < view.getWidth() && points[1] < view.getHeight();
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
                onItemMove();
            }
        });
        mAnimator.start();

    }

    private void onItemMove() {
        //更新滑动百分比
        mOffsetPercent = mOffsetX/getWidth();
        //更新子View的出发点和目的地
        updateChildFromAndTo();
        //更新子View的层级顺序
        updateChildrenOrder();
        //更新子View的透明度和缩放比例
        updateChildrenAlphaAndScale();
        //请求重新布局
        requestLayout();
    }

    /**
     * 更新子View的透明度和缩放比例
     */
    private void updateChildrenAlphaAndScale() {
        //遍历子View
        for(int a = 0;a<getChildCount();a++){
            updateAlphaAndScale(getChildAt(a));
        }
    }

    private void updateAlphaAndScale(View child) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        switch (lp.from){
            //最左面的子View
            case 0:
                //如果目的地是最右面的话
                if(lp.to == 1){
                    //要把他放在最低，为了避免在移动过程中遮挡其他子View，透明度和缩放比例不变
                    exchangeOrder(indexOfChild(child),0);
                }else if(lp.to == 2){//如果目的地是中间的话
                    lp.alpha = mMinAlpha + (1 - mMinAlpha) * mOffsetPercent;
                    lp.scale = mMinScale + (1 - mMinScale) * mOffsetPercent;
                }
                break;
            //最右面的子View
            case 1:
                if(lp.to == 0){
                    exchangeOrder(indexOfChild(child),0);
                }else if(lp.to == 2){
                    lp.alpha = mMinAlpha + (1 - mMinAlpha) * -mOffsetPercent;
                    lp.scale = mMinScale + (1 - mMinScale) * -mOffsetPercent;
                }
                break;
            //中间的子View
            case 2:
                //这里就不用判断   因为无论向哪一边   透明度和缩放比都是减小
                lp.alpha = 1 - (1 - mMinAlpha) * Math.abs(mOffsetPercent);
                lp.scale = 1 - (1 - mMinScale) * Math.abs(mOffsetPercent);
                break;
        }

    }

    /**
     * 更新子View的层级顺序
     */
    private void updateChildrenOrder() {
        //如果滑动距离超过ViewGroup宽度的一半，就把索引为1,2的子View交换顺序，并标记已经交换过
        if(Math.abs(mOffsetPercent)>0.5){
            if(!isReordered){
                exchangeOrder(1,2);
                isReordered = true;
            }
        }else{
            //滑动距离没有超过一半，即有可能是滑动超过一半之后然后滑动回来
            //如果isReordered = true 就表示本次滑动已经交换过顺序  所以要在交换一次
            if(isReordered){
                exchangeOrder(1,2);
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

    /**
     * 更新子View的出发点和目的地
     */
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
                    //最左面的子View，如果是向右滑动，那么他的目的地是中间，也就是2
                    //如果想左滑动，目的地是最右面的位置，也就是1
                    lp.to = mOffsetPercent>0?2:1;

                }else if(lp.from == 1){
                    //最右面的子View  如果向右滑动，那么目的地为最左面 0，反之为正中间2
                    lp.to = mOffsetPercent>0?0:2;
                }else{
                    //中间的子View   如果向右滑动  那么目的地为 最右面1 反之为最左面 0
                    lp.to = mOffsetPercent>0?1:0;
                }
            }
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    /**
     * 主要用来存储子View的透明度和缩放 ， 同时支持Mardin
     */
    static class LayoutParams extends MarginLayoutParams{

        int to, from;
        float scale;
        float alpha;

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
