package com.booboomx.mycount.mvp.register;

import com.booboomx.mycount.base.BasePresenter;
import com.booboomx.mycount.base.BaseView;
import com.booboomx.mycount.data.User;
import com.booboomx.mycount.data.Error;

/**
 * Created by booboomx on 17/7/24.
 */

public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showRegisterSuccess();
        void showRegisterFail(Error e);
        void showVerifyPhoneSuccess();
        void showVerifyPhoneFail(Error e);
    }

    interface Presenter extends BasePresenter {
        void register(User user);
        void verifyPhone(String code);
    }
}
