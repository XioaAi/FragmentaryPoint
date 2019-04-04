package com.zxd.xiaoabapp.base;

import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.interfaces.ITitleRightClick;
import com.zxd.xiaoabapp.utils.DensityUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private TextView titleTv;
    private LinearLayout rightLayout;
    private TextView backView;
    Toolbar toolbar;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        //initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            layoutParams.height = DensityUtils.dip2px(BaseActivity.this, 45);
            toolbar.setLayoutParams(layoutParams);
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            layoutParams.height = DensityUtils.dip2px(BaseActivity.this, 70);
            toolbar.setLayoutParams(layoutParams);
        }
        backView = (TextView) findViewById(R.id.backView);
        titleTv = (TextView) findViewById(R.id.toolbar_center_title);
        rightLayout = (LinearLayout) findViewById(R.id.toolbar_right_layout);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }


    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
        ButterKnife.bind(this);
    }

    public void setToolBarTitle(String strTitle) {
        if (titleTv != null) {
            titleTv.setText(strTitle);
        }
    }

    /**
     * 隐藏返回键
     */
    public void hideBackView() {
        if (backView != null) backView.setVisibility(View.GONE);
    }

    /**
     * 显示返回键
     */
    public void showBackView() {
        if (backView != null) backView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏导航栏
     */
    public void hideToolBar() {
        if (toolbar != null) toolbar.setVisibility(View.GONE);
    }

    /**
     * 显示导航栏
     */
    public void showToolBar() {
        if (toolbar != null) toolbar.setVisibility(View.VISIBLE);
    }

    /**
     * 添加title右侧布局
     *
     * @param view
     */
    public void addToolBarRightLayout(View view) {
        if (rightLayout != null) {
            rightLayout.addView(view);
        }
    }

    public void addToolBarRightLayout(View view, final ITitleRightClick iTitleRightClick) {
        if (rightLayout != null) {
            rightLayout.addView(view);
            rightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iTitleRightClick.configClick();
                }
            });
        }
    }

    /**
     * 清除title右侧布局
     */
    public void hideToolBarRightLayout() {
        if (rightLayout.getChildCount() > 0) {
            rightLayout.removeAllViews();
        }
    }

    /**
     * 修改toolbar颜色
     * @param isUpdate
     */
    public void updateToolBarBg(boolean isUpdate,int colorRes){
        if (toolbar != null && isUpdate) toolbar.setBackgroundColor(getResources().getColor(colorRes));
        else toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }


    @OnClick(R.id.backView)
    public void onBackClick() {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }
        }.start();
    }

}
