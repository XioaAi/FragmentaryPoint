package com.zxd.xiaoabapp.ui;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.adapter.FragmentAdapter;
import com.zxd.xiaoabapp.fragments.ListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorLayout1Activity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout1);
        ButterKnife.bind(this);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.white));
        initViewPager();
    }

    private void initViewPager() {
        List<String> titles = new ArrayList();
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");

        for(int i=0;i<titles.size();i++){
            //mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();
        for(int i=0;i<titles.size();i++){
            fragments.add(new ListFragment());
        }
        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapteradapter);
        ViewCompat.setElevation(mTabLayout, 10);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置适配器
        //mTabLayout.setTabsFromPagerAdapter(mFragmentAdapteradapter);

        //自定义Tab样式
        /*for (int i = 0; i < mFragmentAdapteradapter.getCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.tab_layout_item);//给每一个tab设置view
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab.getCustomView().findViewById(R.id.list_fragment).setSelected(true);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/


    }
}
