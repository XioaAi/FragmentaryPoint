package com.zxd.xiaoabapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.base.BaseActivity;
import com.zxd.xiaoabapp.fragments.ListFragment;
import com.zxd.xiaoabapp.fragments.OneFragment;
import com.zxd.xiaoabapp.fragments.TwoFragment;
import com.zxd.xiaoabapp.view.ViewPagerTabLinearlayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragmentActivity extends BaseActivity implements View.OnClickListener{

    /*private TextView tv_item_one;
    private TextView tv_item_two;
    private TextView tv_item_three;*/
    private ViewPagerTabLinearlayout viewPagerTabLinearlayout;
    private ViewPager myViewPager;
    private List<Fragment> list;
    private TabFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment);

        InitView();

// 设置菜单栏的点击事件
        /*tv_item_one.setOnClickListener(this);
        tv_item_two.setOnClickListener(this);
        tv_item_three.setOnClickListener(this);*/
        //myViewPager.setOnPageChangeListener(new MyPagerChangeListener());
        List<String> mTabs = new ArrayList<>();
        mTabs.add("tab1");
        mTabs.add("tab2");
        mTabs.add("tab3");
        viewPagerTabLinearlayout.init(this,myViewPager,mTabs);
//把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new OneFragment());
        list.add(new ListFragment());
        list.add(new TwoFragment());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面

        //tv_item_one.setBackgroundColor(getResources().getColor(R.color.red));//被选中就为红色
    }

    /**
     * 初始化控件
     */
    private void InitView() {
        /*tv_item_one = (TextView) findViewById(R.id.tv_item_one);
        tv_item_two = (TextView) findViewById(R.id.tv_item_two);
        tv_item_three = (TextView) findViewById(R.id.tv_item_three);*/
        viewPagerTabLinearlayout = findViewById(R.id.tab_layout);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.tv_item_one:
                myViewPager.setCurrentItem(0);
                tv_item_one.setBackgroundColor(getResources().getColor(R.color.red));
                tv_item_two.setBackgroundColor(getResources().getColor(R.color.white));
                tv_item_three.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_item_two:
                myViewPager.setCurrentItem(1);
                tv_item_one.setBackgroundColor(getResources().getColor(R.color.white));
                tv_item_two.setBackgroundColor(getResources().getColor(R.color.red));
                tv_item_three.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_item_three:
                myViewPager.setCurrentItem(2);
                tv_item_one.setBackgroundColor(getResources().getColor(R.color.white));
                tv_item_two.setBackgroundColor(getResources().getColor(R.color.white));
                tv_item_three.setBackgroundColor(getResources().getColor(R.color.red));
                break;
        }*/
    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    /*public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    tv_item_one.setBackgroundColor(getResources().getColor(R.color.red));
                    tv_item_two.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_item_three.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 1:
                    tv_item_one.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_item_two.setBackgroundColor(getResources().getColor(R.color.red));
                    tv_item_three.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 2:
                    tv_item_one.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_item_two.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_item_three.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
            }
        }
    }*/

    class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager mfragmentManager;
        private List<Fragment> mlist;

        public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mlist = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mlist.get(arg0);//显示第几个页面
        }

        @Override
        public int getCount() {
            return mlist.size();//有几个页面
        }
    }
}
