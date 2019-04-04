package com.zxd.xiaoabapp.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.adapter.MainPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager pager;
    private MainPageAdapter adapter;
    private List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        pager = findViewById(R.id.main_page);
        if (pager == null) return;
        // pageCount设置红缓存的页面数
        pager.setOffscreenPageLimit(3);
        // 设置2个item之前的间距。
        pager.setPageMargin(20);
        list = new ArrayList<>();
        String[] strings = {"123", "456", "789", "147", "258", "369"};
        addView(strings);
        adapter = new MainPageAdapter(list, getApplicationContext());
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        pager.setPageMargin(50);

        //解决只能滑动中间的那个View
        findViewById(R.id.ly).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return pager.dispatchTouchEvent(event);
            }
        });
    }

    private void addView(String[] strings) {
        for (String str : strings) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.main_item, null);
            TextView textView = (TextView) view.findViewById(R.id.item_text);
            textView.setText(str);
            list.add(view);
        }
    }



}
