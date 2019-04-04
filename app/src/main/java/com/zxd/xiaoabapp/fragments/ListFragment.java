package com.zxd.xiaoabapp.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.zxd.xiaoabapp.R;

/**
 * Created by Administrator on 2018/3/21.
 */
public class ListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View contentView = inflater.inflate(R.layout.list_fragment, container, false);
        /*ListView mContentLv = (ListView) contentView.findViewById(R.id.lv_content);
        //mContentLv.setOnItemClickListener(this);
        //解决ListView  ScrollView不支持滑动的问题
        ViewCompat.setNestedScrollingEnabled(mContentLv, true);
        mContentLv.setAdapter(new ContentAdapter());*/
        ScrollView scrollView = contentView.findViewById(R.id.scrollView);
        ViewCompat.setNestedScrollingEnabled(scrollView, true);
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Tag","ListFragment_onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Tag","ListFragment_onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Tag","ListFragment_onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Tag","ListFragment_onPause");
    }

    /*private class ContentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_simple_list_2, null);
            ImageView coverIv = (ImageView) contentView.findViewById(R.id.iv_cover);
            coverIv.setImageResource(getResources().getIdentifier("ic_palette_0"+position%4, "mipmap", getActivity().getPackageName()));
            contentView.findViewById(R.id.cv_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*Intent detailIntent = new Intent(getActivity(), PaletteDetailActivity.class);
                    detailIntent.putExtra(PaletteDetailActivity.EXTRA_INDEX, position);
                    startActivity(detailIntent);*//*
                }
            });
            return contentView;
        }
    }*/
}
