package com.booboomx.mycount.mvp.login;

import android.text.TextUtils;
import android.util.Log;

import com.booboomx.mycount.data.Error;
import com.booboomx.mycount.data.User;
import com.booboomx.mycount.data.source.UserDataSource;
import com.booboomx.mycount.data.source.UserRepository;

/**
 * Created by booboomx on 17/7/27.
 */

public class LoginPresenter implements  LoginContract.Presenter{

    public static final String TAG=LoginPresenter.class.getSimpleName();

    private UserRepository mUserRepository;
    private LoginContract.View mView;

    public LoginPresenter(UserRepository userRepository, LoginContract.View view) {
        mUserRepository = userRepository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean checkUserInfo(User user) {
        String password = user.getPassword();
        String phone = user.getPhone();

        if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)){
            return false;
        }

        return true;
    }

    @Override
    public void login(User user) {

        mUserRepository.login(user, new UserDataSource.LoginCallback() {
            @Override
            public void loginSuccess() {
                mView.showLoginSuccess();


            }

            @Override
            public void loginFail(Error e) {
                Log.i(TAG, "loginFail: "+e.getMessage());

                mView.showLoginFail(e);

            }
        });


    }

    @Override
    public void requestPhoneVerify(String phone) {

        mUserRepository.requestPhoneVerify(phone, new UserDataSource.SendVerifyCodeCallback() {
            @Override
            public void sendVerifyCodeSuccess() {
                mView.showVerifyPhoneSuccess();
            }

            @Override
            public void sendVerifyCodeFail(Error e) {
                mView.showSendVerifyCodeFail(e);
            }
        });

    }

    @Override
    public void verifyPhone(String code) {
        mUserRepository.verifyPhone(code, new UserDataSource.VerifyPhoneCallback() {
            @Override
            public void verifySuccess() {
                mView.showVerifyPhoneSuccess();
            }

            @Override
            public void verifyFail(Error e) {
                mView.showVerifyPhoneFail(e);

            }
        });

    }
}
