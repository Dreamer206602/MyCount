package com.booboomx.mycount.mvp.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.booboomx.mycount.R;
import com.booboomx.mycount.base.BaseActivity;
import com.booboomx.mycount.data.source.UserRepository;
import com.booboomx.mycount.utils.ActivityUtils;

/**
 * 登录的界面
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setSlideable(false);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        Toolbar toolbar = initTooBar("");
        toolbar.setNavigationIcon(null);


        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.container);


        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.container);
        }


        new LoginPresenter(new UserRepository(), fragment);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


}

