package com.booboomx.mycount.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboomx.mycount.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by booboomx on 17/7/17.
 */

public abstract class BaseFragment extends Fragment {

    public View mView;
    public Toolbar mToolbar;
    public Activity mActivity;
    public Context mContext;
    public static String TAG;
    private AppCompatActivity mCompatActivity;

    // 是否可见
    protected boolean mIsVisiable;
    // 是否已经调用了 onCreateView
    protected boolean mIsViewCreate;
    private Unbinder bind;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mActivity = getActivity();
        mContext = mActivity;
        mCompatActivity = (AppCompatActivity) mActivity;
        setHasOptionsMenu(true);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getFragmentLayoutId(), container, false);
        return mView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mIsViewCreate = true;
        bind = ButterKnife.bind(this, mView);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCreateFragment(savedInstanceState);
    }

    public Toolbar initToolBar(String title) {
        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mCompatActivity.setSupportActionBar(mToolbar);

        ActionBar actionBar = mCompatActivity.getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        //设置标题
        if (!TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
        }


        //设置左侧的坐标
        mToolbar.setNavigationIcon(R.mipmap.ic_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBack();
            }
        });


        return mToolbar;


    }

    public void setTitleTextColor(int id) {
        if (mToolbar != null) {
            mToolbar.setTitleTextColor(id);
        }

    }

    public void onBack() {

        finish();
    }


    /**
     * 销毁当前挂载的Activity
     */
    public void finish() {
        mActivity.finish();
    }


    protected abstract void onCreateFragment(Bundle savedInstanceState);


    public abstract int getFragmentLayoutId();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisiable=isVisibleToUser;
        if(mIsViewCreate&&mIsVisiable){
            onLazyLoadData();
        }
    }

    protected abstract void onLazyLoadData();


    @Override
    public void onDestroyView() {
        bind.unbind();
        super.onDestroyView();
    }
}
