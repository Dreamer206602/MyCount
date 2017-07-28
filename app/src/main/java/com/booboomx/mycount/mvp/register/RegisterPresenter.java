package com.booboomx.mycount.mvp.register;

import android.util.Log;

import com.booboomx.mycount.data.User;
import com.booboomx.mycount.data.source.UserDataSource;
import com.booboomx.mycount.data.source.UserRepository;
import com.booboomx.mycount.data.Error;

/**
 * Created by booboomx on 17/7/24.
 */

public class RegisterPresenter implements RegisterContract.Presenter {


    private final UserRepository mUserRepository;
    private final RegisterContract.View mView;

    public static final String TAG = RegisterPresenter.class.getSimpleName();

    public RegisterPresenter(UserRepository userRepository, RegisterContract.View view) {
        mUserRepository = userRepository;
        mView = view;
        mView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void register(User user) {
        mView.setLoadingIndicator(true);
        mUserRepository.register(user, new UserDataSource.RegisterCallback() {
            @Override
            public void registerSuccess() {
                mView.setLoadingIndicator(false);
                mView.showRegisterSuccess();
            }

            @Override
            public void registerFail(Error e) {
                mView.setLoadingIndicator(false);
                mView.showRegisterFail(e);

            }
        });

    }

    @Override
    public void verifyPhone(String code) {

        mView.setLoadingIndicator(true);
        Log.i(TAG, "verifyPhone: "+code);
        mUserRepository.verifyPhone(code, new UserDataSource.VerifyPhoneCallback() {
            @Override
            public void verifySuccess() {
                mView.setLoadingIndicator(false);
                mView.showVerifyPhoneSuccess();
            }

            @Override
            public void verifyFail(Error e) {
                Log.i(TAG, "verifyFail: "+e.getMessage());
                mView.setLoadingIndicator(false);
                mView.showVerifyPhoneFail(e);


            }
        });

    }
}
