package com.booboomx.mycount.mvp.register;

import android.os.Bundle;

import com.booboomx.mycount.R;
import com.booboomx.mycount.base.BaseActivity;
import com.booboomx.mycount.data.source.UserRepository;
import com.booboomx.mycount.utils.ActivityUtils;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {


    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

        initTooBar("注册");

        RegisterFragment fragment= (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.container);

        if(fragment==null){
            fragment=RegisterFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),fragment,R.id.container);


        }


        new RegisterPresenter(new UserRepository(),fragment);



    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }
}

