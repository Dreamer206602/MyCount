package com.booboomx.mycount.mvp.login;

import com.booboomx.mycount.base.BasePresenter;
import com.booboomx.mycount.base.BaseView;
import com.booboomx.mycount.data.User;
import com.booboomx.mycount.data.Error;
/**
 * Created by booboomx on 17/7/27.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showLoginSuccess();
        void showLoginFail(Error e);
        void showSendVerifyCodeSuccess();
        void showSendVerifyCodeFail(Error e);
        void showVerifyPhoneSuccess();
        void showVerifyPhoneFail(Error e);
        void createDefaultType(String uid);
    }

    interface Presenter extends BasePresenter {
        boolean checkUserInfo(User user);
        void login(User user);
        void requestPhoneVerify(String phone);
        void verifyPhone(String code);
    }


}
