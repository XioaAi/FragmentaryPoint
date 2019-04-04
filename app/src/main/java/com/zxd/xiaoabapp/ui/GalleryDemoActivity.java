package com.zxd.xiaoabapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.view.MyGalleryView;

import java.util.ArrayList;
import java.util.List;

public class GalleryDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_demo);
        MyGalleryView gallery = findViewById(R.id.gallery);
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.banner3);
        images.add(R.mipmap.banner1);
        images.add(R.mipmap.banner1);
        images.add(R.mipmap.banner3);
        images.add(R.mipmap.banner1);
        gallery.setImages(images);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GalleryDemoActivity.this,""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
