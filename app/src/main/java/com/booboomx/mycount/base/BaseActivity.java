package com.booboomx.mycount.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.booboomx.mycount.R;
import com.booboomx.mycount.utils.ActivityManager;
import com.booboomx.mycount.utils.KeyBoardUtils;
import com.booboomx.mycount.widget.slideback.SlideBackActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by booboomx on 17/7/17.
 */

public abstract class BaseActivity extends SlideBackActivity {
    public static String TAG;
    public Activity mContext;
    public Toolbar mToolbar;
    public Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //设置屏幕的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置不自动弹出键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //隐藏ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //设置TAG
        TAG = this.getClass().getSimpleName();
        this.mContext = this;
        super.onCreate(savedInstanceState);

        // 设置布局
        setContentView(getLayoutId());

        //绑定依赖注入框架
        mUnbinder = ButterKnife.bind(this);

        onCreateActivity(savedInstanceState);
        // 将当前的Activity推入栈中
        ActivityManager.getInstance().pushActivity(this);

    }

    protected abstract void onCreateActivity(Bundle savedInstanceState);

    public abstract int getLayoutId();


    /**
     * 初始化标题栏
     *
     * @param titlle
     * @return
     */
    public Toolbar initTooBar(String titlle) {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //设置标题
        if (!TextUtils.isEmpty(titlle)) {
            mToolbar.setTitle(titlle);
        }

        //设置左侧的图标
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


    private void onBack() {

        finish();

    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        ActivityManager.getInstance().popActivity(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }


    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }

    }

    public void jumpActivity(Class<?> tClass) {
        startActivity(new Intent(mContext, tClass));
//        finish();
    }


    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                //隐藏键盘
                KeyBoardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }


}
