package com.zxd.xiaoabapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mcxiaoke.packer.helper.PackerNg;
import com.zxd.xiaoabapp.base.BaseActivity;
import com.zxd.xiaoabapp.ui.AIDLActivity;
import com.zxd.xiaoabapp.ui.AnimationSetActivity;
import com.zxd.xiaoabapp.ui.CollapsingToolBarLayoutActivity;
import com.zxd.xiaoabapp.ui.CoordinatorLayout1Activity;
import com.zxd.xiaoabapp.ui.CustomProgressViewActivity;
import com.zxd.xiaoabapp.ui.CustomViewActivity;
import com.zxd.xiaoabapp.ui.CustomViewBitmapActivity;
import com.zxd.xiaoabapp.ui.CustomViewGroupActivity;
import com.zxd.xiaoabapp.ui.DrawArcActivity;
import com.zxd.xiaoabapp.ui.DrawScrollActivity;
import com.zxd.xiaoabapp.ui.GalleryDemoActivity;
import com.zxd.xiaoabapp.ui.LitePagerActivity;
import com.zxd.xiaoabapp.ui.MVVMDemoActivity;
import com.zxd.xiaoabapp.ui.MessengerActivity;
import com.zxd.xiaoabapp.ui.PagerSlidingTabStripActivity;
import com.zxd.xiaoabapp.ui.ShoppingActivity;
import com.zxd.xiaoabapp.ui.SweetAlertDialogActivity;
import com.zxd.xiaoabapp.ui.TextInputLayoutActivity;
import com.zxd.xiaoabapp.ui.ViewPagerActivity;
import com.zxd.xiaoabapp.ui.ViewPagerFragmentActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mvvm)
    Button btn;

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.custom_img_test);
        Toast.makeText(this, "渠道名称:"+PackerNg.getMarket(this),Toast.LENGTH_LONG).show();
    }


    @OnClick({R.id.custom_img_test,R.id.pager_sliding_tab,R.id.tab_viewpager_tab,R.id.shopping_tab,R.id.arc_tab,R.id.gallery,
        R.id.viewpager,R.id.TextInputLayout,R.id.CoordinatorLayout,R.id.CoordinatorLayout1,R.id.animation,R.id.messenger,
        R.id.aidl,R.id.custom_view,R.id.custom_view_progress,R.id.custom_view_bitmap,R.id.custom_view_viewgroup,R.id.draw_scroll,
        R.id.lite_pager,R.id.mvvm})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.custom_img_test:
                Intent intent = new Intent(this, SweetAlertDialogActivity.class);
                startActivity(intent);
                //btn1.setText("1");
                break;
            case R.id.pager_sliding_tab:
                Intent intent1 = new Intent(this, PagerSlidingTabStripActivity.class);
                startActivity(intent1);
                //btn.setText("123123");
                break;
            case R.id.tab_viewpager_tab:
                //Intent intent2 = new Intent(this, TabsViewPageActivity.class);
                Intent intent2 = new Intent(this, ViewPagerFragmentActivity.class);
                startActivity(intent2);
                //btn1.setText("1234");
                break;
            case R.id.shopping_tab:
                Intent intent3 = new Intent(this, ShoppingActivity.class);
                startActivity(intent3);
                break;
            case R.id.arc_tab:
                Intent intent4 = new Intent(this, DrawArcActivity.class);
                startActivity(intent4);
                break;
            case R.id.gallery:
                Intent intent5 = new Intent(this, GalleryDemoActivity.class);
                startActivity(intent5);
                break;
            case R.id.viewpager:
                Intent intent6 = new Intent(this, ViewPagerActivity.class);
                startActivity(intent6);
                break;
            case R.id.TextInputLayout:
                Intent intent7 = new Intent(this, TextInputLayoutActivity.class);
                startActivity(intent7);
                break;
            case R.id.CoordinatorLayout:
                Intent intent8 = new Intent(this, CoordinatorLayout1Activity.class);
                startActivity(intent8);
                break;
            case R.id.CoordinatorLayout1:
                Intent intent9 = new Intent(this, CollapsingToolBarLayoutActivity.class);
                startActivity(intent9);
                break;
            case R.id.animation:
                Intent intent10 = new Intent(this, AnimationSetActivity.class);
                startActivity(intent10);
                break;
            case R.id.messenger:
                Intent intent11 = new Intent(this, MessengerActivity.class);
                startActivity(intent11);
                break;
            case R.id.aidl:
                Intent intent12 = new Intent(this, AIDLActivity.class);
                startActivity(intent12);
                break;
            case R.id.custom_view:
                Intent intent13 = new Intent(this, CustomViewActivity.class);
                startActivity(intent13);
                break;
            case R.id.custom_view_progress:
                Intent intent14 = new Intent(this, CustomProgressViewActivity.class);
                startActivity(intent14);
                break;
            case R.id.custom_view_bitmap:
                Intent intent15 = new Intent(this, CustomViewBitmapActivity.class);
                startActivity(intent15);
                break;
            case R.id.custom_view_viewgroup:
                Intent intent16 = new Intent(this, CustomViewGroupActivity.class);
                startActivity(intent16);
                break;
            case R.id.draw_scroll:
                Intent intent17 = new Intent(this, DrawScrollActivity.class);
                startActivity(intent17);
                break;
            case R.id.lite_pager:
                Intent intent18 = new Intent(this, LitePagerActivity.class);
                startActivity(intent18);
                break;
            case R.id.mvvm:
                Intent intent19 = new Intent(this, MVVMDemoActivity.class);
                startActivity(intent19);
                break;

        }

    }

}
