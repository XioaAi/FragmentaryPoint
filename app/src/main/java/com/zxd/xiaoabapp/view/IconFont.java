package com.zxd.xiaoabapp.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/8/9.
 */

public class IconFont extends AppCompatTextView {
    public IconFont(Context context) {
        super(context);
        init(context);
    }

    public IconFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "fonts/iconfont.ttf");
        setTypeface(iconfont);
    }
}
