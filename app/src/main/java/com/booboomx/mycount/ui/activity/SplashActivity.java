package com.booboomx.mycount.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.mycount.R;
import com.booboomx.mycount.base.BaseActivity;
import com.booboomx.mycount.data.User;
import com.booboomx.mycount.mvp.login.LoginActivity;
import com.booboomx.mycount.ui.MainActivity;
import com.booboomx.mycount.utils.AppUtils;
import com.booboomx.mycount.utils.UserUtils;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

        //设置版本号
        Typeface typeface = Typeface.createFromAsset(getAssets(), "wwfoot.ttf");
        tvAppName.setTypeface(typeface);
        setVersion();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断用户是否更新了应用和登录
                if (UserUtils.checkLogin()) {

                    User user = UserUtils.getUser();
                    boolean mobilePhoneVerified = user.isMobilePhoneVerified();
                    if (mobilePhoneVerified) {
                        // 进入首页
                        jumpActivity(MainActivity.class);
                    } else {
                        //进入登陆页
                        jumpActivity(LoginActivity.class);
                    }

                } else {
                    //进入登录页
                    jumpActivity(LoginActivity.class);
                }


            }
        }, 2000);

    }


    public void setVersion() {

        tvVersion.setText("V".concat(AppUtils.getAppVersionName()));

        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        tvVersion.startAnimation(animation);
        animation.setDuration(2000);
        animation.start();


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;


    }
}
