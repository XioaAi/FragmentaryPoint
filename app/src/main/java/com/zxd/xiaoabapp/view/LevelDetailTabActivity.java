/*
package com.zxd.xiaoabapp.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xes.chinese.banxue.R;
import com.xes.chinese.banxue.R2;
import com.xes.chinese.banxue.adapter.LevelSecondListAdapter;
import com.xes.chinese.banxue.base.Chsbx_BaseActivity;
import com.xes.chinese.banxue.presenter.LevelDetailTabPresenter;
import com.xes.chinese.banxue.view.view.ILevelDetailTabView;
import com.xes.chinese.banxue.view.weight.ChsGuideLayout;
import com.xes.chinese.chsbcmpt.router.ChsRoutePathContant;

import butterknife.BindView;

*/
/**
 * @Description: 二级关卡界面
 * @ProjectName: CloudLearning_Chinese_Android
 * @Package: com.xes.chinese.banxue.view.activity LevelDetailTabActivity
 * @CreateDate: 2019/4/8 18:40
 * @Version: 1.0
 * @Author: wangziheng
 *//*

@Route(path = ChsRoutePathContant.chs_bx.ACTIVITY_BX_LEVELE_SECONDE_HOME)
public class LevelDetailTabActivity extends Chsbx_BaseActivity implements ILevelDetailTabView {
    @BindView(R2.id.chsbx_level_second_recyclerView)
    RecyclerView mLevelList;
    private LevelDetailTabPresenter mPresenter;
    private LevelSecondListAdapter mAdapter;
    private ChsGuideLayout mGuideLayout;

    @Override
    protected void bindData() {

        mPresenter.getLevelSeondListData();
        mAdapter = new LevelSecondListAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically() {
                return isScrollForRecycler;
            }
        };
        mLevelList.setLayoutManager(gridLayoutManager);
        mLevelList.setAdapter(mAdapter);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RecyclerView.ViewHolder viewHolder = mLevelList.findViewHolderForAdapterPosition(0);
                if (viewHolder != null) {

                    View targetView = viewHolder.itemView;
                    mGuideLayout.setTargetView(targetView);

                    mGuideLayout.invalidate();
                    getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);

                }
            }

        });
    }

    private boolean isScrollForRecycler = false;
    @Override
    protected void initView() {
        super.initView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mPresenter = new LevelDetailTabPresenter(this, this);
        mPresenter.getLevelSeondListData();
        mGuideLayout = new ChsGuideLayout(this);

        View guideChildView = LayoutInflater.from(this).inflate(R.layout.chsbx_level_guide_child_layout,mGuideLayout,false);
        mGuideLayout.setChildView(guideChildView);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getDrawable(R.drawable.test_bird));
        mGuideLayout.setBirdView(imageView);
        mGuideLayout.setParentView((ViewGroup) getWindow().getDecorView());
        setPageTitle("第N关");
        if(mGuideLayout.getVisibility() != View.GONE){
            isScrollForRecycler = false;
        }else {
            isScrollForRecycler = true;
        }
        guideChildView.findViewById(R.id.chsbx_level_second_guide_dialog_cannel_btn).setOnClickListener(v -> {
            // 隐藏蒙层
            mGuideLayout.setVisibility(View.GONE);
            isScrollForRecycler = true;
        });
    }

    @Override
    public int getLayout() {
        return R.layout.chsbx_activity_level_detail_tab_layout;
    }


    @Override
    public void setLevelSecondListData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
*/
